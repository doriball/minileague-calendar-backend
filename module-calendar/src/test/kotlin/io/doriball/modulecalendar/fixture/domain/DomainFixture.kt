package io.doriball.modulecalendar.fixture.domain

import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.domain.event.EventStage
import io.doriball.modulecore.domain.store.Store
import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.modulecore.domain.store.StoreEventRuleStage
import io.doriball.modulecore.domain.store.StoreRegion
import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.enums.StageType
import java.time.LocalDateTime
import java.time.LocalTime

fun eventFixture(
    id: String = "event-1",
    store: Store = storeFixture(),
    name: String = "토요일 미니리그",
    scheduledAt: LocalDateTime = LocalDateTime.now(),
    category: LeagueCategoryType = LeagueCategoryType.OFFICIAL,
    stages: List<EventStage> = listOf(eventStageFixture())
): Event = Event(
    id = id,
    store = store,
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

fun storeFixture(
    id: String = "store-1",
    name: String = "포켓몬 카드샵 용산",
    region: StoreRegion = storeRegionFixture(),
    eventRules: List<StoreEventRule> = listOf(storeEventRuleFixture())
): Store = Store(
    id = id,
    name = name,
    region = region,
    address = "서울시 용산구 한강대로23길 55 아이파크몰 리빙파크 8F",
    mapInformation = "37.529, 126.967",
    sns = "@pokemon_card_shop",
    eventRules = eventRules,
    createdAt = LocalDateTime.now(),
    modifiedAt = LocalDateTime.now()
)

fun storeRegionFixture(
    regionNo: Int = 1,
    name: String = "서울"
): StoreRegion = StoreRegion(
    regionNo = regionNo,
    name = name
)

fun storeEventRuleFixture(
    id: String = "rule-1",
    storeId: String = "store-1",
    name: String = "토요일 미니리그",
    dayOfWeek: DayOfWeekType = DayOfWeekType.SAT,
    category: LeagueCategoryType = LeagueCategoryType.OFFICIAL,
    stages: List<StoreEventRuleStage> = listOf(storeEventRuleStageFixture())
): StoreEventRule = StoreEventRule(
    id = id,
    storeId = storeId,
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

fun storeEventRuleStageFixture(
    stageNo: Int = 1,
    type: StageType = StageType.SWISS,
    roundCount: Int = 3,
    gameCountPerRound: Int = 1
): StoreEventRuleStage = StoreEventRuleStage(
    stageNo = stageNo,
    type = type,
    roundCount = roundCount,
    gameCountPerRound = gameCountPerRound
)