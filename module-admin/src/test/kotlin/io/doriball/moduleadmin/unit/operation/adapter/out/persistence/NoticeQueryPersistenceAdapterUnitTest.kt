package io.doriball.moduleadmin.unit.operation.adapter.out.persistence

import io.doriball.moduleadmin.fixture.document.noticeDocumentFixture
import io.doriball.moduleadmin.operation.adapter.out.persistence.NoticeQueryPersistenceAdapter
import io.doriball.moduleadmin.operation.adapter.out.persistence.repository.NoticeMongoRepository
import io.doriball.moduleadmin.operation.domain.NoticeCreate
import io.doriball.moduleadmin.operation.domain.NoticeUpdate
import io.doriball.moduleinfrastructure.persistence.entity.NoticeDocument
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.springframework.data.mongodb.core.MongoOperations
import java.util.*

@ExtendWith(MockitoExtension::class)
class NoticeQueryPersistenceAdapterUnitTest {

    @InjectMocks
    lateinit var sut: NoticeQueryPersistenceAdapter

    @Mock
    lateinit var mongoOperations: MongoOperations

    @Mock
    lateinit var repository: NoticeMongoRepository

    @Test
    fun 공지사항_목록_조회_성공() {

        // given
        val page = 1
        val size = 10
        given(mongoOperations.count(any(), eq(NoticeDocument::class.java))).willReturn(1L)
        given(mongoOperations.find(any(), eq(NoticeDocument::class.java))).willReturn(listOf(noticeDocumentFixture()))

        // when
        val (result, count) = sut.getNotices(page, size, null, null)

        // then
        assert(result.size == 1)
        assert(count == 1L)
        assert(result[0].id == "notice-1")
        assert(result[0].title == "신규 매장 오픈 안내")
        assert(result[0].content == "용산역 아이파크몰에 새로운 카드샵이 오픈했습니다.")

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
    fun 공지사항_단건_조회_NULL_공지사항_없는경우() {

        // given
        val noticeId = "notice-2"
        given(repository.findById(noticeId)).willReturn(Optional.empty())

        // when
        val result = sut.getNoticeDetail(noticeId)

        // then
        assert(result == null)

    }

    @Test
    fun 공지사항_저장_성공() {

        // given
        val create = NoticeCreate(title="title", content="content")

        // when
        sut.createNotice(create)

        // then
        then(repository).should().save(any())

    }

    @Test
    fun 공지사항_수정_성공() {

        // given
        val update = NoticeUpdate(id="notice-1", title="title", content="content")

        // when
        sut.updateNotice(update)

        // then
        then(repository).should().save(any())

    }

    @Test
    fun 공지사항_삭제_성공() {

        // given
        val noticeId = "notice-1"

        // when
        sut.deleteNotice(noticeId)

        // then
        then(repository).should().deleteById(noticeId)

    }

}