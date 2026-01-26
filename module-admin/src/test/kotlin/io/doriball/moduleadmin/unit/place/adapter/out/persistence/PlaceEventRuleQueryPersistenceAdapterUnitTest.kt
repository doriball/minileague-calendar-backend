package io.doriball.moduleadmin.unit.place.adapter.out.persistence

import io.doriball.moduleadmin.fixture.document.placeEventRuleDocumentFixture
import io.doriball.moduleadmin.place.adapter.out.persistence.PlaceEventRuleQueryPersistenceAdapter
import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceEventRuleMongoRepository
import io.doriball.moduleadmin.place.domain.PlaceEventRuleCreate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleStageCreate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleStageUpdate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleUpdate
import io.doriball.modulecore.domain.enums.DayOfWeekType
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import io.doriball.modulecore.domain.enums.StageType
import io.doriball.modulecore.shared.exception.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import java.time.LocalTime
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class PlaceEventRuleQueryPersistenceAdapterUnitTest {

    @InjectMocks
    lateinit var sut: PlaceEventRuleQueryPersistenceAdapter

    @Mock
    lateinit var repository: PlaceEventRuleMongoRepository

    @Test
    fun 장소_이벤트_규칙_목록_조회_성공() {

        // given
        val placeId = "place-1"
        given(repository.findByPlaceId(placeId)).willReturn(listOf(placeEventRuleDocumentFixture()))

        // when
        val result = sut.getPlaceEventRules(placeId)

        // then
        assert(result.size == 1)
        assert(result[0].placeId == placeId)

    }

    @Test
    fun 장소_이벤트_규칙_저장_성공() {

        // given
        val create = PlaceEventRuleCreate(
            placeId = "place-1",
            name = "test",
            dayOfWeek = DayOfWeekType.SAT,
            scheduledAt = LocalTime.of(14, 0),
            category = LeagueCategoryType.OFFICIAL,
            capacity = 64,
            entryFee = 0L,
            stages = listOf(PlaceEventRuleStageCreate(1, StageType.SWISS, 4, 1))
        )

        // when
        sut.createPlaceEventRule(create)

        // then
        then(repository).should().save(any())

    }

    @Test
    fun 장소_이벤트_규칙_수정_성공() {

        // given
        val update = PlaceEventRuleUpdate(
            id = "rule-1",
            placeId = "place-1",
            name = "test",
            dayOfWeek = DayOfWeekType.SAT,
            scheduledAt = LocalTime.of(14, 0),
            category = LeagueCategoryType.OFFICIAL,
            capacity = 64,
            entryFee = 0L,
            stages = listOf(PlaceEventRuleStageUpdate(1, StageType.SWISS, 4, 1))
        )
        given(repository.findById(update.id)).willReturn(Optional.of(placeEventRuleDocumentFixture()))

        // when
        sut.updatePlaceEventRule(update)

        // then
        then(repository).should().save(any())

    }

    @Test
    fun 장소_이벤트_규칙_수정_실패_규칙이_없는_경우() {

        // given
        val update = PlaceEventRuleUpdate(
            id = "rule-2",
            placeId = "place-1",
            name = "test",
            dayOfWeek = DayOfWeekType.SAT,
            scheduledAt = LocalTime.of(14, 0),
            category = LeagueCategoryType.OFFICIAL,
            capacity = 64,
            entryFee = 0L,
            stages = listOf(PlaceEventRuleStageUpdate(1, StageType.SWISS, 4, 1))
        )
        given(repository.findById(update.id)).willReturn(Optional.empty())

        // when & then
        assertThrows<NotFoundException> {
            sut.updatePlaceEventRule(update)
        }

    }

    @Test
    fun 장소_이벤트_규칙_삭제_성공() {

        // given
        val placeId = "place-1"
        val ruleId = "rule-1"

        // when
        sut.deletePlaceEventRule(placeId, ruleId)

        // then
        then(repository).should().deleteByIdAndPlaceId(ruleId, placeId)

    }

}