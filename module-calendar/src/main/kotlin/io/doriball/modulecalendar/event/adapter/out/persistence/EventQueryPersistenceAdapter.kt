package io.doriball.modulecalendar.event.adapter.out.persistence

import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventMongoRepository
import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventStoreMongoRepository
import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventStoreRegionMongoRepository
import io.doriball.modulecalendar.event.application.port.out.EventPort
import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class EventQueryPersistenceAdapter(
    val eventRepository: EventMongoRepository,
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
            val events = eventRepository.findByScheduledAtBetween(startedAt, endedAt)
            val storeIds = events.map { it.storeId }.distinct()
            val stores = storeRepository.findByIdIn(storeIds)
            val regionNos = stores.map { it.regionNo }.distinct()
            val regions = storeRegionRepository.findByRegionNoIn(regionNos)
            Triple(events, stores, regions)
        } else {
            val stores = storeRepository.findByRegionNo(regionNo)
            val region = storeRegionRepository.findByRegionNo(regionNo) ?: return emptyList()
            val storeIds = stores.mapNotNull { it.id }
            val events = eventRepository.findByScheduledAtBetweenAndStoreIdIn(startedAt, endedAt, storeIds)
            Triple(events, stores, listOf(region))
        }

        if (eventDocuments.isEmpty()) return emptyList()

        val regionMap = storeRegionDocuments.associateBy { it.regionNo }
        val storeMap = storeDocuments.associateBy(
            { it.id },
            { doc ->
                val regionDocument = regionMap[doc.regionNo] ?: throw NotFoundException()
                DocumentConvertUtil.convertToStore(doc, DocumentConvertUtil.convertToStoreRegion(regionDocument))
            }
        )

        return eventDocuments.map { eventDocument ->
            val store = storeMap[eventDocument.storeId] ?: throw NotFoundException()
            DocumentConvertUtil.convertToEvent(eventDocument, store)
        }

    }

    override fun getEventDetail(eventId: String): Event? {
        val eventDocument = eventRepository.findByIdOrNull(eventId) ?: return null
        val storeDocument = storeRepository.findByIdOrNull(eventDocument.storeId) ?: return null
        val storeRegion = storeRegionRepository.findByRegionNo(storeDocument.regionNo) ?: return null
        val store =
            DocumentConvertUtil.convertToStore(storeDocument, DocumentConvertUtil.convertToStoreRegion(storeRegion))
        return DocumentConvertUtil.convertToEvent(eventDocument, store)
    }

}