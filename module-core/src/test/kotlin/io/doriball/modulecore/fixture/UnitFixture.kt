package io.doriball.modulecore.fixture

import io.doriball.modulecore.domain.event.Event
import io.doriball.modulecore.domain.event.EventStage
import io.doriball.modulecore.domain.place.Place
import io.doriball.modulecore.domain.place.PlaceEventRule
import io.doriball.modulecore.domain.place.PlaceEventRuleStage
import io.doriball.modulecore.domain.place.PlaceRegion
import io.doriball.modulecore.enums.DayOfWeekType
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.enums.StageType
import io.doriball.modulecore.enums.PlaceType
import java.time.LocalDateTime
import java.time.LocalTime

fun fixtureEvent() = Event(
    id = "1",
    place = fixturePlace(),
    name = "비크티니 쟁탈전",
    scheduledAt = LocalDateTime.now(),
    category = LeagueCategoryType.OFFICIAL,
    capacity = 64,
    entryFee = 0L,
    stages = listOf(fixtureEventStage()),
    createdAt = null,
    modifiedAt = null
)

fun fixtureEventStage() = EventStage(
    stageNo = 1,
    type = StageType.SWISS,
    roundCount = 4,
    gameCountPerRound = 1
)

fun fixturePlace() = Place(
    id = "1",
    name = "포켓몬 카드샵 용산",
    region = PlaceRegion(regionNo = 1, name = "서울"),
    type = PlaceType.STORE,
    address = "address",
    mapInformation = null,
    sns = null,
    eventRules = listOf(),
    createdAt = null,
    modifiedAt = null
)

fun fixturePlaceEventRule() = PlaceEventRule(
    id = "1",
    placeId = "1",
    name = "용산 월요일 미니리그",
    dayOfWeek = DayOfWeekType.MON,
    scheduledAt = LocalTime.of(19, 0),
    category = LeagueCategoryType.OFFICIAL,
    capacity = 64,
    stages = listOf(fixturePlaceEventRuleStage()),
    entryFee = 0L,
    createdAt = null,
    modifiedAt = null
)

fun fixturePlaceEventRuleStage() = PlaceEventRuleStage(
    stageNo = 1,
    type = StageType.SWISS,
    roundCount = 4,
    gameCountPerRound = 1
)