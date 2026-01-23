package io.doriball.moduleadmin.unit.operation.application.service

import io.doriball.moduleadmin.fixture.domain.noticeFixture
import io.doriball.moduleadmin.operation.application.port.`in`.dto.CreateNoticeCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.ReadNoticeDetailCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.ReadNoticesCommand
import io.doriball.moduleadmin.operation.application.port.`in`.dto.UpdateNoticeCommand
import io.doriball.moduleadmin.operation.application.port.out.NoticePort
import io.doriball.moduleadmin.operation.application.service.NoticeServiceV1
import io.doriball.modulecore.exception.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class NoticeServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: NoticeServiceV1

    @Mock
    lateinit var port: NoticePort

    @Test
    fun 공지사항_목록_조회_성공() {

        // given
        val command = ReadNoticesCommand(1, 10, null, null)
        given(port.getNotices(command.page, command.size, command.search, command.keyword))
            .willReturn(listOf(noticeFixture()) to 1L)

        // when
        val (result, size) = sut.getNotices(command)

        // then
        assert(result.size == 1)
        assert(size == 1L)
        assert(result[0].title == "신규 매장 오픈 안내")

    }

    @Test
    fun 공지사항_상세_조회_성공() {

        // given
        val command = ReadNoticeDetailCommand("notice-1")
        given(port.getNoticeDetail(command.noticeId)).willReturn(noticeFixture())

        // when
        val result = sut.getNoticeDetail(command.noticeId)

        // then
        assert(result.id == "notice-1")
        assert(result.title == "신규 매장 오픈 안내")
        assert(result.content == "용산역 아이파크몰에 새로운 카드샵이 오픈했습니다.")

    }

    @Test
    fun 공지사항_상세_조회_실패_존재하지않는_공지사항() {

        // given
        val command = ReadNoticeDetailCommand("notice-2")
        given(port.getNoticeDetail(command.noticeId)).willReturn(null)

        // when & then
        assertThrows<NotFoundException> {
            sut.getNoticeDetail(command.noticeId)
        }

    }

    @Test
    fun 공지사항_등록_성공() {

        // given
        val command = CreateNoticeCommand("title", "content")

        // when
        sut.createNotice(command)

        // then
        then(port).should().createNotice(any())

    }

    @Test
    fun 공지사항_수정_성공() {

        // given
        val noticeId = "notice-1"
        val command = UpdateNoticeCommand("title", "content")

        // when
        sut.updateNotice(noticeId, command)

        // then
        then(port).should().updateNotice(any())

    }

    @Test
    fun 공지사항_삭제_성공() {

        // given
        val noticeId = "notice-1"

        // when
        sut.deleteNotice(noticeId)

        // then
        then(port).should().deleteNotice(noticeId)

    }

}