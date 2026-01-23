package io.doriball.moduleinfrastructure.persistence.util

import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.domain.event.EventStage
import io.doriball.modulecore.domain.operation.Notice
import io.doriball.modulecore.domain.place.Place
import io.doriball.modulecore.domain.place.PlaceEventRule
import io.doriball.modulecore.domain.place.PlaceEventRuleStage
import io.doriball.modulecore.domain.place.PlaceRegion
import io.doriball.modulecore.enums.StageType
import io.doriball.moduleinfrastructure.persistence.entity.NoticeDocument
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceEventRuleDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceRegionDocument

object DocumentConvertUtil {

    fun convertToEvent(eventDocument: EventDocument, place: Place): Event =
        Event(
            id = eventDocument.id!!,
            place = place,
            name = eventDocument.name,
            scheduledAt = eventDocument.scheduledAt,
            category = eventDocument.category,
            capacity = eventDocument.capacity,
            stages = eventDocument.stages.map { convertToEventStage(it) }.toList(),
            entryFee = eventDocument.entryFee,
            createdAt = eventDocument.createdAt,
            modifiedAt = eventDocument.modifiedAt
        )

    fun convertToEventStage(document: StageDocument): EventStage =
        EventStage(
            stageNo = document.stageNo,
            type = StageType.valueOf(document.type),
            roundCount = document.roundCount,
            gameCountPerRound = document.gameCountPerRound
        )

    fun convertToPlace(placeDocument: PlaceDocument, placeRegion: PlaceRegion): Place =
        Place(
            id = placeDocument.id!!,
            name = placeDocument.name,
            region = placeRegion,
            type = placeDocument.type,
            address = placeDocument.address,
            mapInformation = placeDocument.mapInformation,
            sns = placeDocument.sns,
            createdAt = placeDocument.createdAt,
            modifiedAt = placeDocument.modifiedAt
        )

    fun convertToPlaceRegion(document: PlaceRegionDocument): PlaceRegion =
        PlaceRegion(regionNo = document.regionNo, name = document.name)

    fun convertToNotice(noticeDocument: NoticeDocument) = Notice(
        id = noticeDocument.id!!,
        title = noticeDocument.title,
        content = noticeDocument.content,
        createdAt = noticeDocument.createdAt,
        modifiedAt = noticeDocument.modifiedAt
    )

    fun convertToPlaceEventRule(document: PlaceEventRuleDocument) = PlaceEventRule(
        id = document.id!!,
        placeId = document.placeId,
        name = document.name,
        dayOfWeek = document.dayOfWeek,
        scheduledAt = document.scheduledAt,
        category = document.category,
        capacity = document.capacity,
        stages = document.stages.map { convertToPlaceEventRuleStage(it) }.toList(),
        entryFee = document.entryFee,
        createdAt = document.createdAt,
        modifiedAt = document.modifiedAt
    )

    fun convertToPlaceEventRuleStage(document: StageDocument): PlaceEventRuleStage =
        PlaceEventRuleStage(
            stageNo = document.stageNo,
            type = StageType.valueOf(document.type),
            roundCount = document.roundCount,
            gameCountPerRound = document.gameCountPerRound
        )

}