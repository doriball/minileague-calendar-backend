package io.doriball.modulecalendar.event.adapter.out.persistence

import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventMongoRepository
import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventPlaceMongoRepository
import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventPlaceRegionMongoRepository
import io.doriball.modulecalendar.event.application.port.out.EventPort
import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.shared.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class EventQueryPersistenceAdapter(
    private val eventRepository: EventMongoRepository,
    private val placeRegionRepository: EventPlaceRegionMongoRepository,
    private val placeRepository: EventPlaceMongoRepository,
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
            val placeIds = events.map { it.placeId }.distinct()
            val places = placeRepository.findByIdIn(placeIds)
            val regionNos = places.map { it.regionNo }.distinct()
            val regions = placeRegionRepository.findByRegionNoIn(regionNos)
            Triple(events, places, regions)
        } else {
            val places = placeRepository.findByRegionNo(regionNo)
            val region = placeRegionRepository.findByRegionNo(regionNo) ?: return emptyList()
            val placeIds = places.mapNotNull { it.id }
            val events = eventRepository.findByScheduledAtBetweenAndPlaceIdIn(startedAt, endedAt, placeIds)
            Triple(events, places, listOf(region))
        }

        if (eventDocuments.isEmpty()) return emptyList()

        val regionMap = storeRegionDocuments.associateBy { it.regionNo }
        val storeMap = storeDocuments.associateBy(
            { it.id },
            { doc ->
                val regionDocument = regionMap[doc.regionNo] ?: throw NotFoundException()
                DocumentConvertUtil.convertToPlace(doc, DocumentConvertUtil.convertToPlaceRegion(regionDocument))
            }
        )

        return eventDocuments.map { eventDocument ->
            val place = storeMap[eventDocument.placeId] ?: throw NotFoundException()
            DocumentConvertUtil.convertToEvent(eventDocument, place)
        }

    }

    override fun getEventDetail(eventId: String): Event? {
        val eventDocument = eventRepository.findByIdOrNull(eventId) ?: return null
        val placeDocument = placeRepository.findByIdOrNull(eventDocument.placeId) ?: return null
        val placeRegion = placeRegionRepository.findByRegionNo(placeDocument.regionNo) ?: return null
        val store =
            DocumentConvertUtil.convertToPlace(placeDocument, DocumentConvertUtil.convertToPlaceRegion(placeRegion))
        return DocumentConvertUtil.convertToEvent(eventDocument, store)
    }

}