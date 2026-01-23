package io.doriball.modulecalendar.fixture.document

import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.enums.StageType
import io.doriball.modulecore.enums.PlaceType
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import io.doriball.moduleinfrastructure.persistence.entity.NoticeDocument
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceEventRuleDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceRegionDocument
import java.time.LocalDateTime
import java.time.LocalTime

fun eventDocumentFixture(
    id: String = "event-1",
    placeId: String = "place-1",
    name: String = "토요일 미니리그",
    scheduledAt: LocalDateTime = LocalDateTime.now(),
    category: LeagueCategoryType = LeagueCategoryType.OFFICIAL,
    stages: List<StageDocument> = listOf(stageDocumentFixture())
): EventDocument = EventDocument(
    placeId = placeId,
    name = name,
    scheduledAt = scheduledAt,
    category = category,
    capacity = 64,
    stages = stages,
    entryFee = 0L
).apply { this.id = id }

fun placeDocumentFixture(
    id: String = "place-1",
    name: String = "포켓몬 카드샵 용산",
    regionNo: Int = 1,
    type: PlaceType = PlaceType.STORE,
): PlaceDocument = PlaceDocument(
    name = name,
    regionNo = regionNo,
    type = type,
    address = "서울시 용산구 한강대로23길 55 아이파크몰 리빙파크 8F",
    mapInformation = "37.529, 126.967",
    sns = "@pokemon_card_shop"
).apply { this.id = id }

fun placeRegionDocumentFixture(
    id: String = "region-1",
    regionNo: Int = 1,
    name: String = "서울"
): PlaceRegionDocument = PlaceRegionDocument(
    regionNo = regionNo,
    name = name
).apply { this.id = id }

fun placeEventRuleDocumentFixture(
    id: String = "rule-1",
    placeId: String = "place-1",
    name: String = "토요일 미니리그",
    dayOfWeek: DayOfWeekType = DayOfWeekType.SAT,
    category: LeagueCategoryType = LeagueCategoryType.OFFICIAL,
    stages: List<StageDocument> = listOf(stageDocumentFixture())
): PlaceEventRuleDocument = PlaceEventRuleDocument(
    placeId = placeId,
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