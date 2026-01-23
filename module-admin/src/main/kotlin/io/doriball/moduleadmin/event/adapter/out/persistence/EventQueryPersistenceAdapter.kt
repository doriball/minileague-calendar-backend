package io.doriball.moduleadmin.event.adapter.out.persistence

import io.doriball.moduleadmin.event.adapter.out.persistence.repository.EventMongoRepository
import io.doriball.moduleadmin.event.adapter.out.persistence.repository.EventPlaceMongoRepository
import io.doriball.moduleadmin.event.adapter.out.persistence.repository.EventPlaceRegionMongoRepository
import io.doriball.moduleadmin.event.application.port.out.EventPort
import io.doriball.moduleadmin.event.common.enums.EventKeywordSearchType
import io.doriball.moduleadmin.event.domain.EventCreate
import io.doriball.moduleadmin.event.domain.EventStageCreate
import io.doriball.moduleadmin.event.domain.EventStageUpdate
import io.doriball.moduleadmin.event.domain.EventUpdate
import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class EventQueryPersistenceAdapter(
    val mongoOperations: MongoOperations,
    val eventRepository: EventMongoRepository,
    val placeRepository: EventPlaceMongoRepository,
    val placeRegionRepository: EventPlaceRegionMongoRepository,
) : EventPort {

    override fun getEvents(
        year: Int,
        month: Int,
        regionNo: Int?,
        official: Boolean?,
        searchType: EventKeywordSearchType?,
        keyword: String?,
    ): List<Event> {

        val startedAt = LocalDateTime.of(year, month, 1, 0, 0)
        val endedAt = startedAt.plusMonths(1).minusSeconds(1)

        val query = Query()

        // 기본 조건: 날짜 범위
        query.addCriteria(Criteria.where("scheduledAt").gte(startedAt).lte(endedAt))

        // Official 조건
        official?.let {
            query.addCriteria(Criteria.where("official").`is`(it))
        }

        // Region 및 Keyword (Store 검색) 처리
        if (regionNo != null || (searchType == EventKeywordSearchType.STORE && !keyword.isNullOrBlank())) {
            val placeQuery = Query()
            regionNo?.let { placeQuery.addCriteria(Criteria.where("regionNo").`is`(it)) }
            if (searchType == EventKeywordSearchType.STORE && !keyword.isNullOrBlank()) {
                placeQuery.addCriteria(Criteria.where("name").regex(keyword, "i"))
            }

            val matchingPlaceIds = mongoOperations.find(placeQuery, PlaceDocument::class.java)
                .mapNotNull { it.id }

            if (matchingPlaceIds.isEmpty()) return emptyList()
            query.addCriteria(Criteria.where("placeId").`in`(matchingPlaceIds))
        }

        // Keyword 조건 (Event 검색)
        if (!keyword.isNullOrBlank()) {
            if (searchType == null || searchType == EventKeywordSearchType.EVENT) {
                query.addCriteria(Criteria.where("name").regex(keyword, "i"))
            }
        }

        // 정렬: scheduledAt 최신순
        query.with(Sort.by(Sort.Direction.DESC, "scheduledAt"))

        val eventDocuments = mongoOperations.find(query, EventDocument::class.java)
        if (eventDocuments.isEmpty()) return emptyList()

        val placeIds = eventDocuments.map { it.placeId }.distinct()
        val places = placeRepository.findByIdIn(placeIds)
        val regions =
            placeRegionRepository.findByRegionNoIn(places.map { it.regionNo }.distinct()).associateBy { it.regionNo }
        val placeMap = places.associateBy(
            { it.id },
            { placeDocument ->
                val regionDoc = regions[placeDocument.regionNo] ?: throw NotFoundException()
                DocumentConvertUtil.convertToPlace(placeDocument, DocumentConvertUtil.convertToPlaceRegion(regionDoc))
            }
        )

        return eventDocuments.map { eventDocument ->
            val store = placeMap[eventDocument.placeId] ?: throw NotFoundException()
            DocumentConvertUtil.convertToEvent(eventDocument, store)
        }
    }

    override fun getEventDetail(eventId: String): Event? {
        val eventDocument = eventRepository.findByIdOrNull(eventId) ?: return null
        val storeDocument = placeRepository.findByIdOrNull(eventDocument.placeId) ?: return null
        val storeRegion = placeRegionRepository.findByRegionNo(storeDocument.regionNo) ?: return null
        val store =
            DocumentConvertUtil.convertToPlace(storeDocument, DocumentConvertUtil.convertToPlaceRegion(storeRegion))
        return DocumentConvertUtil.convertToEvent(eventDocument, store)
    }

    override fun isPastEvent(eventId: String): Boolean {
        val eventDocument = eventRepository.findByIdOrNull(eventId) ?: return false
        return eventDocument.scheduledAt.isBefore(LocalDateTime.now())
    }

    override fun createEvent(create: EventCreate) {
        val document = toEventDocument(create)
        eventRepository.save(document)
    }

    override fun updateEvent(update: EventUpdate) {
        val document = toEventDocument(update)
        eventRepository.save(document)
    }

    override fun deleteEvent(eventId: String) {
        eventRepository.deleteById(eventId)
    }

    private fun toEventDocument(create: EventCreate) = EventDocument(
        placeId = create.placeId,
        name = create.name,
        scheduledAt = create.scheduledAt,
        category = create.category,
        capacity = create.capacity,
        entryFee = create.entryFee,
        stages = create.stages.map { toEventStageDocument(it) }.toList()
    )

    private fun toEventStageDocument(create: EventStageCreate) = StageDocument(
        stageNo = create.stageNo,
        type = create.type.name,
        roundCount = create.roundCount,
        gameCountPerRound = create.gameCount
    )

    private fun toEventDocument(update: EventUpdate) = EventDocument(
        placeId = update.placeId,
        name = update.name,
        scheduledAt = update.scheduledAt,
        category = update.category,
        capacity = update.capacity,
        entryFee = update.entryFee,
        stages = update.stages.map { toEventStageDocument(it) }.toList()
    ).apply { id = update.id }

    private fun toEventStageDocument(update: EventStageUpdate) = StageDocument(
        stageNo = update.stageNo,
        type = update.type.name,
        roundCount = update.roundCount,
        gameCountPerRound = update.gameCount
    )

}