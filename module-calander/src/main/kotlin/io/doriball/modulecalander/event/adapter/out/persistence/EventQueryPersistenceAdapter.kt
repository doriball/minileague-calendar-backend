package io.doriball.modulecalander.event.adapter.out.persistence

import io.doriball.modulecalander.event.adapter.out.persistence.repository.EventMongoRepository
import io.doriball.modulecalander.event.adapter.out.persistence.repository.EventStoreMongoRepository
import io.doriball.modulecalander.event.adapter.out.persistence.repository.EventStoreRegionMongoRepository
import io.doriball.modulecalander.event.application.port.out.EventPort
import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.domain.event.EventStage
import io.doriball.modulecore.domain.store.Store
import io.doriball.modulecore.domain.store.StoreRegion
import io.doriball.modulecore.enums.StageType
import io.doriball.modulecore.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreRegionDocument
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class EventQueryPersistenceAdapter(
    val eventMongoRepository: EventMongoRepository,
    val storeRegionRepository: EventStoreRegionMongoRepository,
    val storeRepository: EventStoreMongoRepository,
) : EventPort {

    override fun getEvents(
        year: Int,
        month: Int,
        regionNo: Int?
    ): List<Event> {

        val startedAt = LocalDateTime.of(year, month, 1, 0, 0)
        val endedAt = startedAt.plusMonths(1).minusSeconds(1)

        val (eventDocuments, storeDocuments, storeRegionDocuments) = if (regionNo == null) {
            val events = eventMongoRepository.findByScheduledAtBetween(startedAt, endedAt)
            val storeIds = events.map { it.storeId }.distinct()
            val stores = storeRepository.findByIdIn(storeIds)
            val regionNos = stores.map { it.regionNo }.distinct()
            val regions = storeRegionRepository.findByRegionNoIn(regionNos)
            Triple(events, stores, regions)
        }
        else {
            val stores = storeRepository.findByRegionNo(regionNo)
            val region = storeRegionRepository.findByRegionNo(regionNo) ?: return emptyList()
            val storeIds = stores.map { it.id }
            val events = eventMongoRepository.findByScheduledAtBetweenAndStoreIdIn(startedAt, endedAt, storeIds)
            Triple(events, stores, listOf(region))
        }

        if (eventDocuments.isEmpty()) return emptyList()

        val regionMap = storeRegionDocuments.associateBy { it.regionNo }
        val storeMap = storeDocuments.associateBy(
            { it.id },
            { doc ->
                val regionDocument = regionMap[doc.regionNo] ?: throw NotFoundException()
                convertToStore(doc, convertToStoreRegion(regionDocument))
            }
        )

        return eventDocuments.map { eventDocument ->
            val store = storeMap[eventDocument.storeId] ?: throw NotFoundException()
            convertToEvent(eventDocument, store)
        }

    }

    override fun getEventDetail(eventId: String): Event? {
        val eventDocument = eventMongoRepository.findByIdOrNull(eventId) ?: return null
        val storeDocument = storeRepository.findByIdOrNull(eventDocument.storeId) ?: return null
        val storeRegion = storeRegionRepository.findByRegionNo(storeDocument.regionNo) ?: return null
        val store = convertToStore(storeDocument, convertToStoreRegion(storeRegion))
        return convertToEvent(eventDocument, store)
    }

    private fun convertToEvent(eventDocument: EventDocument, store: Store): Event =
        Event(
            id = eventDocument.id,
            store = store,
            name = eventDocument.name,
            scheduledAt = eventDocument.scheduledAt,
            official = eventDocument.official,
            stages = eventDocument.stages.map { convertToEventStage(it) }.toList(),
            createdAt = eventDocument.createdAt,
            modifiedAt = eventDocument.modifiedAt
        )

    private fun convertToEventStage(document: StageDocument): EventStage =
        EventStage(
            stageNo = document.stageNo,
            type = StageType.valueOf(document.type),
            roundCount = document.roundCount,
            gameCountPerRound = document.gameCountPerRound
        )

    private fun convertToStore(storeDocument: StoreDocument, storeRegion: StoreRegion): Store =
        Store(
            id = storeDocument.id,
            name = storeDocument.name,
            region = storeRegion,
            address = storeDocument.address,
            mapInformation = storeDocument.mapInformation,
            sns = storeDocument.sns,
            createdAt = storeDocument.createdAt,
            modifiedAt = storeDocument.modifiedAt
        )

    private fun convertToStoreRegion(document: StoreRegionDocument): StoreRegion =
        StoreRegion(regionNo = document.regionNo, name = document.name)

}