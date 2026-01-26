package io.doriball.moduleadmin.unit.place.application.service

import io.doriball.moduleadmin.fixture.domain.placeEventRuleFixture
import io.doriball.moduleadmin.place.application.port.`in`.dto.CreatePlaceEventRuleCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.CreatePlaceEventRuleStageCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.ReadPlaceEventRulesCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.UpdatePlaceEventRuleCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.UpdatePlaceEventRuleStageCommand
import io.doriball.moduleadmin.place.application.port.out.PlaceEventRulePort
import io.doriball.moduleadmin.place.application.service.PlaceEventRuleServiceV1
import io.doriball.modulecore.domain.enums.DayOfWeekType
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import io.doriball.modulecore.domain.enums.StageType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import java.time.LocalTime

@ExtendWith(MockitoExtension::class)
class PlaceEventRuleServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: PlaceEventRuleServiceV1

    @Mock
    lateinit var port: PlaceEventRulePort

    @Test
    fun 장소_이벤트_규칙_목록_조회_성공() {

        // given
        val command = ReadPlaceEventRulesCommand("place-1")
        given(port.getPlaceEventRules(command.placeId)).willReturn(listOf(placeEventRuleFixture()))

        // when
        val result = sut.getPlaceEventRules(command)

        // then
        assert(result.size == 1)
        assert(result[0].placeId == "place-1")

    }

    @Test
    fun 장소_이벤트_규칙_등록_성공() {

        // given
        val placeId = "place-1"
        val command = CreatePlaceEventRuleCommand(
            name = "test",
            dayOfWeek = DayOfWeekType.SAT,
            scheduledAt = LocalTime.of(14, 0),
            category = LeagueCategoryType.OFFICIAL,
            capacity = 64,
            entryFee = 0L,
            stages = listOf(CreatePlaceEventRuleStageCommand(1, StageType.SWISS, 4, 1))
        )

        // when
        sut.createPlaceEventRule(placeId, command)

        // then
        then(port).should().createPlaceEventRule(any())

    }

    @Test
    fun 장소_이벤트_규칙_수정_성공() {

        // given
        val placeId = "place-1"
        val ruleId = "rule-1"
        val command = UpdatePlaceEventRuleCommand(
            name = "test",
            dayOfWeek = DayOfWeekType.SAT,
            scheduledAt = LocalTime.of(14, 0),
            category = LeagueCategoryType.OFFICIAL,
            capacity = 64,
            entryFee = 0L,
            stages = listOf(UpdatePlaceEventRuleStageCommand(1, StageType.SWISS, 4, 1))
        )

        // when
        sut.updatePlaceEventRule(placeId, ruleId, command)

        // then
        then(port).should().updatePlaceEventRule(any())

    }

    @Test
    fun 장소_이벤트_규칙_삭제_성공() {

        // given
        val placeId = "place-1"
        val ruleId = "rule-1"

        // when
        sut.deletePlaceEventRule(placeId, ruleId)

        // then
        then(port).should().deletePlaceEventRule(any(), any())

    }

}