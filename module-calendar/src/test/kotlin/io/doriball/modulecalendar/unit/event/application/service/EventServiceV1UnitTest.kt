package io.doriball.modulecalendar.unit.event.application.service

import io.doriball.modulecalendar.event.application.port.`in`.dto.ReadEventDetailCommand
import io.doriball.modulecalendar.event.application.port.`in`.dto.ReadEventsCommand
import io.doriball.modulecalendar.event.application.port.out.EventPort
import io.doriball.modulecalendar.event.application.service.EventServiceV1
import io.doriball.modulecalendar.fixture.domain.eventFixture
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.enums.StageType
import io.doriball.modulecore.exception.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class EventServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: EventServiceV1

    @Mock
    lateinit var port: EventPort

    @Test
    fun 이벤트_일정_목록_조회_성공() {

        // given
        val command = ReadEventsCommand(2022, 1, 1)
        given(port.getEvents(command.year, command.month, command.regionNo)).willReturn(listOf(eventFixture()))

        // when
        val result = sut.getEvents(command)

        // then
        assert(result.size == 1)
        assert(result[0].id == "event-1")
        assert(result[0].name == "토요일 미니리그")
        assert(result[0].region == "서울")
        assert(result[0].place == "포켓몬 카드샵 용산")
        assert(result[0].category == LeagueCategoryType.OFFICIAL)
        assert(result[0].capacity == 64)
        assert(result[0].entryFee == 0L)
        assert(result[0].stages.size == 1)
        assert(result[0].stages[0].stageNo == 1)
        assert(result[0].stages[0].type == StageType.SWISS)
        assert(result[0].stages[0].roundCount == 4)
        assert(result[0].stages[0].gameCount == 1)

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
        assert(result.place == "포켓몬 카드샵 용산")
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
    fun 이벤트_일정_상세_조회_실패_존재하지_않는_일정() {

        // given
        val command = ReadEventDetailCommand("event-2")
        given(port.getEventDetail(command.eventId)).willReturn(null)

        // when & then
        assertThrows<NotFoundException> {
            sut.getEventDetail(command)
        }

    }

}