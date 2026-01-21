package io.doriball.modulecalendar.unit.operation.adapter.out.persistence

import io.doriball.modulecalendar.fixture.document.noticeDocumentFixture
import io.doriball.modulecalendar.operation.adapter.out.persistence.NoticeQueryPersistenceAdapter
import io.doriball.modulecalendar.operation.adapter.out.persistence.repository.NoticeMongoRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.util.*

@ExtendWith(MockitoExtension::class)
class NoticeQueryPersistenceAdapterUnitTest {

    @InjectMocks
    lateinit var sut: NoticeQueryPersistenceAdapter

    @Mock
    lateinit var repository: NoticeMongoRepository

    @Test
    fun 공지사항_목록_조회_성공() {

        // given
        val page = 1
        val size = 10
        val pageable = PageRequest.of(0, 10)
        val notices = listOf(noticeDocumentFixture())
        val pageResult = PageImpl(notices, pageable, notices.size.toLong())
        given(repository.findAll(any<Pageable>())).willReturn(pageResult)

        // when
        val (result, totalElements) = sut.getNotices(page, size)

        // then
        assert(result.size == 1)
        assert(result[0].id == "notice-1")
        assert(result[0].title == "신규 매장 오픈 안내")
        assert(result[0].content == "용산역 아이파크몰에 새로운 카드샵이 오픈했습니다.")
        assert(totalElements == 1L)

    }

    @Test
    fun 공지사항_단건_조회_성공() {

        // given
        val noticeId = "notice-1"
        given(repository.findById(noticeId)).willReturn(Optional.of(noticeDocumentFixture()))

        // when
        val result = sut.getNoticeDetail(noticeId)!!

        // then
        assert(result.id == "notice-1")
        assert(result.title == "신규 매장 오픈 안내")
        assert(result.content == "용산역 아이파크몰에 새로운 카드샵이 오픈했습니다.")

    }

    @Test
    fun 공지사항_단건_조회_NULL_공지사항이_없는_경우() {

        // given
        val noticeId = "notice-2"
        given(repository.findById(noticeId)).willReturn(Optional.empty())

        // when
        val result = sut.getNoticeDetail(noticeId)

        // then
        assert(result == null)

    }

}