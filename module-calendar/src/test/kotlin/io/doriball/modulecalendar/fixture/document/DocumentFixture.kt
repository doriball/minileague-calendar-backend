package io.doriball.modulecalendar.fixture.document

import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.enums.StageType
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import io.doriball.moduleinfrastructure.persistence.entity.NoticeDocument
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreEventRuleDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreRegionDocument
import java.time.LocalDateTime
import java.time.LocalTime

fun eventDocumentFixture(
    id: String = "event-1",
    storeId: String = "store-1",
    name: String = "토요일 미니리그",
    scheduledAt: LocalDateTime = LocalDateTime.now(),
    category: LeagueCategoryType = LeagueCategoryType.OFFICIAL,
    stages: List<StageDocument> = listOf(stageDocumentFixture())
): EventDocument = EventDocument(
    storeId = storeId,
    name = name,
    scheduledAt = scheduledAt,
    category = category,
    capacity = 64,
    stages = stages,
    entryFee = 0L
).apply { this.id = id }

fun storeDocumentFixture(
    id: String = "store-1",
    name: String = "포켓몬 카드샵 용산",
    regionNo: Int = 1
): StoreDocument = StoreDocument(
    name = name,
    regionNo = regionNo,
    address = "서울시 용산구 한강대로23길 55 아이파크몰 리빙파크 8F",
    mapInformation = "37.529, 126.967",
    sns = "@pokemon_card_shop"
).apply { this.id = id }

fun storeRegionDocumentFixture(
    id: String = "region-1",
    regionNo: Int = 1,
    name: String = "서울"
): StoreRegionDocument = StoreRegionDocument(
    regionNo = regionNo,
    name = name
).apply { this.id = id }

fun storeEventRuleDocumentFixture(
    id: String = "rule-1",
    storeId: String = "store-1",
    name: String = "토요일 미니리그",
    dayOfWeek: DayOfWeekType = DayOfWeekType.SAT,
    category: LeagueCategoryType = LeagueCategoryType.OFFICIAL,
    stages: List<StageDocument> = listOf(stageDocumentFixture())
): StoreEventRuleDocument = StoreEventRuleDocument(
    storeId = storeId,
    name = name,
    dayOfWeek = dayOfWeek,
    scheduledAt = LocalTime.of(14, 0),
    category = category,
    capacity = 64,
    stages = stages,
    entryFee = 0L
).apply { this.id = id }

fun stageDocumentFixture(
    stageNo: Int = 1,
    type: StageType = StageType.SWISS,
    roundCount: Int = 4,
    gameCountPerRound: Int = 1
): StageDocument = StageDocument(
    stageNo = stageNo,
    type = type.name,
    roundCount = roundCount,
    gameCountPerRound = gameCountPerRound
)

fun noticeDocumentFixture(
    id: String = "notice-1",
    title: String = "신규 매장 오픈 안내",
    content: String = "용산역 아이파크몰에 새로운 카드샵이 오픈했습니다."
): NoticeDocument = NoticeDocument(
    title = title,
    content = content
).apply { this.id = id }