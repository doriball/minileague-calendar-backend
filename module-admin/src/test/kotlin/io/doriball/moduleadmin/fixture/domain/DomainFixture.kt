package io.doriball.moduleadmin.fixture.domain

import io.doriball.moduleadmin.place.domain.PlaceSummary
import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.domain.event.EventStage
import io.doriball.modulecore.domain.operation.Notice
import io.doriball.modulecore.domain.place.Place
import io.doriball.modulecore.domain.place.PlaceEventRule
import io.doriball.modulecore.domain.place.PlaceEventRuleStage
import io.doriball.modulecore.domain.place.PlaceRegion
import io.doriball.modulecore.domain.enums.DayOfWeekType
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import io.doriball.modulecore.domain.enums.StageType
import io.doriball.modulecore.domain.enums.PlaceType
import java.time.LocalDateTime
import java.time.LocalTime

fun eventFixture(
    id: String = "event-1",
    place: Place = placeFixture(),
    name: String = "토요일 미니리그",
    scheduledAt: LocalDateTime = LocalDateTime.now(),
    category: LeagueCategoryType = LeagueCategoryType.OFFICIAL,
    stages: List<EventStage> = listOf(eventStageFixture())
): Event = Event(
    id = id,
    place = place,
    name = name,
    scheduledAt = scheduledAt,
    category = category,
    capacity = 64,
    stages = stages,
    entryFee = 0L,
    createdAt = LocalDateTime.now(),
    modifiedAt = LocalDateTime.now()
)

fun eventStageFixture(
    stageNo: Int = 1,
    type: StageType = StageType.SWISS,
    roundCount: Int = 4,
    gameCountPerRound: Int = 1
): EventStage = EventStage(
    stageNo = stageNo,
    type = type,
    roundCount = roundCount,
    gameCountPerRound = gameCountPerRound
)

fun placeFixture(
    id: String = "place-1",
    name: String = "포켓몬 카드샵 용산",
    region: PlaceRegion = placeRegionFixture(),
    type: PlaceType = PlaceType.STORE,
    eventRules: List<PlaceEventRule> = listOf(placeEventRuleFixture())
): Place = Place(
    id = id,
    name = name,
    region = region,
    type = type,
    address = "서울시 용산구 한강대로23길 55 아이파크몰 리빙파크 8F",
    mapInformation = "37.529, 126.967",
    sns = "@pokemon_card_shop",
    eventRules = eventRules,
    createdAt = LocalDateTime.now(),
    modifiedAt = LocalDateTime.now()
)

fun placeSummaryFixture(
    id: String = "place-1",
    name: String = "포켓몬 카드샵 용산",
): PlaceSummary = PlaceSummary(
    id = id,
    name = name
)

fun placeRegionFixture(
    regionNo: Int = 1,
    name: String = "서울"
): PlaceRegion = PlaceRegion(
    regionNo = regionNo,
    name = name
)

fun placeEventRuleFixture(
    id: String = "rule-1",
    placeId: String = "place-1",
    name: String = "토요일 미니리그",
    dayOfWeek: DayOfWeekType = DayOfWeekType.SAT,
    category: LeagueCategoryType = LeagueCategoryType.OFFICIAL,
    stages: List<PlaceEventRuleStage> = listOf(placeEventRuleStageFixture())
): PlaceEventRule = PlaceEventRule(
    id = id,
    placeId = placeId,
    name = name,
    dayOfWeek = dayOfWeek,
    scheduledAt = LocalTime.of(14, 0),
    category = category,
    capacity = 64,
    stages = stages,
    entryFee = 0L,
    createdAt = LocalDateTime.now(),
    modifiedAt = LocalDateTime.now()
)

fun placeEventRuleStageFixture(
    stageNo: Int = 1,
    type: StageType = StageType.SWISS,
    roundCount: Int = 3,
    gameCountPerRound: Int = 1
): PlaceEventRuleStage = PlaceEventRuleStage(
    stageNo = stageNo,
    type = type,
    roundCount = roundCount,
    gameCountPerRound = gameCountPerRound
)

fun noticeFixture(
    id: String = "notice-1",
    title: String = "신규 매장 오픈 안내",
    content: String = "용산역 아이파크몰에 새로운 카드샵이 오픈했습니다."
): Notice = Notice(
    id = id,
    title = title,
    content = content,
    createdAt = LocalDateTime.now(),
    modifiedAt = LocalDateTime.now()
)