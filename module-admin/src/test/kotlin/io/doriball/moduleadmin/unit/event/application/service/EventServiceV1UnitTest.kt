package io.doriball.moduleadmin.unit.event.application.service

import io.doriball.moduleadmin.event.application.port.`in`.dto.*
import io.doriball.moduleadmin.event.application.port.out.EventPort
import io.doriball.moduleadmin.event.application.service.EventServiceV1
import io.doriball.moduleadmin.event.common.exception.EventIsPassedException
import io.doriball.moduleadmin.fixture.domain.eventFixture
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.enums.StageType
import io.doriball.modulecore.exception.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import java.time.LocalDateTime

@ExtendWith(MockitoExtension::class)
class EventServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: EventServiceV1

    @Mock
    lateinit var port: EventPort

    @Test
    fun 이벤트_일정_목록_조회_성공() {

        // given
        val command = ReadEventsCommand(2025, 1, null, null, null, null)
        given(
            port.getEvents(
                command.year,
                command.month,
                command.regionNo,
                command.official,
                command.search,
                command.keyword
            )
        ).willReturn(listOf(eventFixture()))

        // when
        val result = sut.getEvents(command)

        // then
        assert(result.size == 1)
        assert(result[0].id == "event-1")
        assert(result[0].name == "토요일 미니리그")
        assert(result[0].region == "서울")
        assert(result[0].category == LeagueCategoryType.OFFICIAL)
        assert(result[0].types[0] == "SWISS")
        assert(result[0].roundCount == 4)
        assert(result[0].gameCount == 1)

    }

    @Test
    fun 이벤트_일정_상세_조회_성공() {

        // given
        val command = ReadEventDetailCommand("event-1")
        given(port.getEventDetail(command.eventId)).willReturn(eventFixture())

        // when
        val result = sut.getEventDetail(command)

        // then
        assert(result.id == "event-1")
        assert(result.name == "토요일 미니리그")
        assert(result.region == "서울")
        assert(result.placeName == "포켓몬 카드샵 용산")
        assert(result.category == LeagueCategoryType.OFFICIAL)
        assert(result.capacity == 64)
        assert(result.entryFee == 0L)
        assert(result.stages.size == 1)
        assert(result.stages[0].stageNo == 1)
        assert(result.stages[0].type == StageType.SWISS)
        assert(result.stages[0].roundCount == 4)
        assert(result.stages[0].gameCount == 1)

    }

    @Test
    fun 이벤트_일정_상세_조회_실패_존재하지않는_일정() {

        // given
        val command = ReadEventDetailCommand("event-2")
        given(port.getEventDetail(command.eventId)).willReturn(null)

        // when & then
        assertThrows<NotFoundException> {
            sut.getEventDetail(command)
        }

    }

    @Test
    fun 이벤트_일정_등록_성공() {

        // given
        val command = CreateEventCommand(
            name = "event", placeId = "store-1", scheduledAt = LocalDateTime.now(),
            category = LeagueCategoryType.OFFICIAL, capacity = 64, entryFee = 0L, stages = listOf(
                CreateEventStageCommand(stageNo = 1, type = StageType.SWISS, roundCount = 4, gameCount = 1)
            )
        )

        // when
        sut.createEvent(command)

        // then
        then(port).should().createEvent(any())

    }

    @Test
    fun 이벤트_일정_수정_성공() {

        // given
        val eventId = "event-1"
        val command = UpdateEventCommand(
            name = "event", placeId = "store-1", scheduledAt = LocalDateTime.now(),
            category = LeagueCategoryType.OFFICIAL, capacity = 64, entryFee = 0L, stages = listOf(
                UpdateEventStageCommand(stageNo = 1, type = StageType.SWISS, roundCount = 4, gameCount = 1)
            )
        )

        // when
        sut.updateEvent(eventId, command)

        // then
        then(port).should().updateEvent(any())

    }

    @Test
    fun 이벤트_일정_삭제_성공() {

        // given
        val eventId = "event-1"
        given(port.isPastEvent(eventId)).willReturn(false)

        // when
        sut.deleteEvent(eventId)

        // then
        then(port).should().isPastEvent(eventId)
        then(port).should().deleteEvent(eventId)

    }

    @Test
    fun 이벤트_일정_삭제_실패_과거에_등록된_일정() {

        // given
        val eventId = "event-1"
        given(port.isPastEvent(eventId)).willReturn(true)

        // when & then
        assertThrows<EventIsPassedException> {
            sut.deleteEvent(eventId)
        }

    }

}