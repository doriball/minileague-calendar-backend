package io.doriball.moduleinfrastructure.persistence.util

import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.domain.event.EventStage
import io.doriball.modulecore.domain.operation.Notice
import io.doriball.modulecore.domain.store.Store
import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.modulecore.domain.store.StoreEventRuleStage
import io.doriball.modulecore.domain.store.StoreRegion
import io.doriball.modulecore.enums.StageType
import io.doriball.moduleinfrastructure.persistence.entity.NoticeDocument
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreEventRuleDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreRegionDocument

object DocumentConvertUtil {

    fun convertToEvent(eventDocument: EventDocument, store: Store): Event =
        Event(
            id = eventDocument.id!!,
            store = store,
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

    fun convertToStore(storeDocument: StoreDocument, storeRegion: StoreRegion): Store =
        Store(
            id = storeDocument.id!!,
            name = storeDocument.name,
            region = storeRegion,
            address = storeDocument.address,
            mapInformation = storeDocument.mapInformation,
            sns = storeDocument.sns,
            createdAt = storeDocument.createdAt,
            modifiedAt = storeDocument.modifiedAt
        )

    fun convertToStoreRegion(document: StoreRegionDocument): StoreRegion =
        StoreRegion(regionNo = document.regionNo, name = document.name)

    fun convertToNotice(noticeDocument: NoticeDocument) = Notice(
        id = noticeDocument.id!!,
        title = noticeDocument.title,
        content = noticeDocument.content,
        createdAt = noticeDocument.createdAt,
        modifiedAt = noticeDocument.modifiedAt
    )

    fun convertToStoreEventRule(document: StoreEventRuleDocument) = StoreEventRule(
        id = document.id!!,
        storeId = document.storeId,
        name = document.name,
        dayOfWeek = document.dayOfWeek,
        scheduledAt = document.scheduledAt,
        category = document.category,
        capacity = document.capacity,
        stages = document.stages.map { convertToStoreEventRuleStage(it) }.toList(),
        entryFee = document.entryFee,
        createdAt = document.createdAt,
        modifiedAt = document.modifiedAt
    )

    fun convertToStoreEventRuleStage(document: StageDocument): StoreEventRuleStage =
        StoreEventRuleStage(
            stageNo = document.stageNo,
            type = StageType.valueOf(document.type),
            roundCount = document.roundCount,
            gameCountPerRound = document.gameCountPerRound
        )

}