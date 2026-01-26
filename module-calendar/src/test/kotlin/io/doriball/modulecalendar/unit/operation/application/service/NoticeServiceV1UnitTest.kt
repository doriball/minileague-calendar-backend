package io.doriball.modulecalendar.unit.operation.application.service

import io.doriball.modulecalendar.fixture.domain.noticeFixture
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticeDetailCommand
import io.doriball.modulecalendar.operation.application.port.`in`.dto.ReadNoticesCommand
import io.doriball.modulecalendar.operation.application.port.out.NoticePort
import io.doriball.modulecalendar.operation.application.service.NoticeServiceV1
import io.doriball.modulecore.shared.exception.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class NoticeServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: NoticeServiceV1

    @Mock
    lateinit var port: NoticePort

    @Test
    fun 공지사항_목록_조회_성공() {

        // given
        val command = ReadNoticesCommand(1, 10)
        given(port.getNotices(command.page, command.size)).willReturn(Pair(listOf(noticeFixture()), 1))

        // when
        val (result, totalCount) = sut.getNotices(command)

        // then
        assert(result.size == 1)
        assert(result[0].id == "notice-1")
        assert(result[0].title == "신규 매장 오픈 안내")
        assert(totalCount == 1L)

    }

    @Test
    fun 공지사항_상세_조회_성공() {

        // given
        val command = ReadNoticeDetailCommand("notice-1")
        given(port.getNoticeDetail(command.noticeId)).willReturn(noticeFixture())

        // when
        val result = sut.getNoticeDetail(command)

        // then
        assert(result.id == "notice-1")
        assert(result.title == "신규 매장 오픈 안내")
        assert(result.content == "용산역 아이파크몰에 새로운 카드샵이 오픈했습니다.")

    }

    @Test
    fun 공지사항_상세_조회_실패_존재하지_않음() {

        // given
        val command = ReadNoticeDetailCommand("notice-2")
        given(port.getNoticeDetail(command.noticeId)).willReturn(null)

        // when & then
        assertThrows<NotFoundException> {
            sut.getNoticeDetail(command)
        }
    }

}