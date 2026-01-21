package io.doriball.modulecalendar.unit.event.adapter.out.persistence

import io.doriball.modulecalendar.event.adapter.out.persistence.EventQueryPersistenceAdapter
import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventMongoRepository
import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventStoreMongoRepository
import io.doriball.modulecalendar.event.adapter.out.persistence.repository.EventStoreRegionMongoRepository
import io.doriball.modulecalendar.fixture.document.eventDocumentFixture
import io.doriball.modulecalendar.fixture.document.storeDocumentFixture
import io.doriball.modulecalendar.fixture.document.storeRegionDocumentFixture
import io.doriball.modulecore.enums.LeagueCategoryType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import java.util.*

@ExtendWith(MockitoExtension::class)
class EventQueryPersistenceAdapterUnitTest {

    @InjectMocks
    lateinit var sut: EventQueryPersistenceAdapter

    @Mock
    lateinit var eventRepository: EventMongoRepository

    @Mock
    lateinit var storeRegionRepository: EventStoreRegionMongoRepository

    @Mock
    lateinit var storeRepository: EventStoreMongoRepository

    @Test
    fun 이벤트_일정_목록_조회_성공_지역_필터링_미적용() {

        // given
        val year = 2026
        val month = 1
        val regionNo = null
        given(eventRepository.findByScheduledAtBetween(any(), any()))
            .willReturn(listOf(eventDocumentFixture()))
        given(storeRepository.findByIdIn(anyList())).willReturn(listOf(storeDocumentFixture()))
        given(storeRegionRepository.findByRegionNoIn(anyList())).willReturn(listOf(storeRegionDocumentFixture()))

        // when
        val result = sut.getEvents(year, month, regionNo)

        // then
        assert(result.size == 1)
        assert(result[0].id == "event-1")
        assert(result[0].name == "토요일 미니리그")
        assert(result[0].regionName == "서울")
        assert(result[0].storeId == "store-1")
        assert(result[0].storeName == "포켓몬 카드샵 용산")
        assert(result[0].category == LeagueCategoryType.OFFICIAL)
        assert(result[0].capacity == 64)
        assert(result[0].entryFee == 0L)
        assert(result[0].stages.size == 1)
        assert(result[0].stageTypes.size == 1)
        assert(result[0].stageTypes[0] == "SWISS")
        assert(result[0].roundCount == 4)
        assert(result[0].gameCount == 1)

    }

    @Test
    fun 이벤트_일정_목록_조회_성공_지역_필터링_적용() {

        // given
        val year = 2026
        val month = 1
        val regionNo = 1
        given(storeRepository.findByRegionNo(regionNo)).willReturn(listOf(storeDocumentFixture()))
        given(storeRegionRepository.findByRegionNo(any())).willReturn(storeRegionDocumentFixture())
        given(eventRepository.findByScheduledAtBetweenAndStoreIdIn(any(), any(), anyList()))
            .willReturn(listOf(eventDocumentFixture()))

        // when
        val result = sut.getEvents(year, month, regionNo)

        // then
        assert(result.size == 1)
        assert(result[0].id == "event-1")
        assert(result[0].name == "토요일 미니리그")
        assert(result[0].regionName == "서울")
        assert(result[0].storeId == "store-1")
        assert(result[0].storeName == "포켓몬 카드샵 용산")
        assert(result[0].category == LeagueCategoryType.OFFICIAL)
        assert(result[0].capacity == 64)
        assert(result[0].entryFee == 0L)
        assert(result[0].stages.size == 1)
        assert(result[0].stageTypes.size == 1)
        assert(result[0].stageTypes[0] == "SWISS")
        assert(result[0].roundCount == 4)
        assert(result[0].gameCount == 1)

    }

    @Test
    fun 이벤트_일정_단건_조회_성공() {

        // given
        val eventId = "event-1"
        given(eventRepository.findById(eventId)).willReturn(Optional.of(eventDocumentFixture()))
        given(storeRepository.findById(eventDocumentFixture().storeId)).willReturn(Optional.of(storeDocumentFixture()))
        given(storeRegionRepository.findByRegionNo(storeDocumentFixture().regionNo)).willReturn(
            storeRegionDocumentFixture()
        )

        // when
        val result = sut.getEventDetail(eventId)!!

        // then
        assert(result.id == "event-1")
        assert(result.name == "토요일 미니리그")
        assert(result.regionName == "서울")
        assert(result.storeId == "store-1")
        assert(result.storeName == "포켓몬 카드샵 용산")
        assert(result.category == LeagueCategoryType.OFFICIAL)
        assert(result.capacity == 64)
        assert(result.entryFee == 0L)
        assert(result.stages.size == 1)
        assert(result.stageTypes.size == 1)
        assert(result.stageTypes[0] == "SWISS")
        assert(result.roundCount == 4)
        assert(result.gameCount == 1)

    }

    @Test
    fun 이벤트_일정_단건_조회_NULL_일정이_없는_경우() {

        // given
        val eventId = "event-2"
        given(eventRepository.findById(eventId)).willReturn(Optional.empty())

        // when
        val result = sut.getEventDetail(eventId)

        // then
        assert(result == null)

    }

    @Test
    fun 이벤트_일정_단건_조회_NULL_매장이_없는_경우() {

        // given
        val eventId = "event-1"
        given(eventRepository.findById(eventId)).willReturn(Optional.of(eventDocumentFixture()))
        given(storeRepository.findById(eventDocumentFixture().storeId)).willReturn(Optional.empty())

        // when
        val result = sut.getEventDetail(eventId)

        // then
        assert(result == null)

    }

    @Test
    fun 이벤트_일정_단건_조회_NULL_지역이_없는_경우() {

        // given
        val eventId = "event-1"
        given(eventRepository.findById(eventId)).willReturn(Optional.of(eventDocumentFixture()))
        given(storeRepository.findById(eventDocumentFixture().storeId)).willReturn(Optional.of(storeDocumentFixture()))
        given(storeRegionRepository.findByRegionNo(storeDocumentFixture().regionNo)).willReturn(null)

        // when
        val result = sut.getEventDetail(eventId)

        // then
        assert(result == null)

    }

}