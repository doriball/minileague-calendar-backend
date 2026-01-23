package io.doriball.moduleadmin.unit.event.adapter.out.persistence

import io.doriball.moduleadmin.event.adapter.out.persistence.EventQueryPersistenceAdapter
import io.doriball.moduleadmin.event.adapter.out.persistence.repository.EventMongoRepository
import io.doriball.moduleadmin.event.adapter.out.persistence.repository.EventPlaceMongoRepository
import io.doriball.moduleadmin.event.adapter.out.persistence.repository.EventPlaceRegionMongoRepository
import io.doriball.moduleadmin.event.domain.EventCreate
import io.doriball.moduleadmin.event.domain.EventStageCreate
import io.doriball.moduleadmin.event.domain.EventStageUpdate
import io.doriball.moduleadmin.event.domain.EventUpdate
import io.doriball.moduleadmin.fixture.document.eventDocumentFixture
import io.doriball.moduleadmin.fixture.document.placeDocumentFixture
import io.doriball.moduleadmin.fixture.document.placeRegionDocumentFixture
import io.doriball.modulecore.enums.LeagueCategoryType
import io.doriball.modulecore.enums.StageType
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.springframework.data.mongodb.core.MongoOperations
import java.time.LocalDateTime
import java.util.*

@ExtendWith(MockitoExtension::class)
class EventQueryPersistenceAdapterUnitTest {

    @InjectMocks
    lateinit var sut: EventQueryPersistenceAdapter

    @Mock
    lateinit var mongoOperations: MongoOperations

    @Mock
    lateinit var eventRepository: EventMongoRepository

    @Mock
    lateinit var placeRepository: EventPlaceMongoRepository

    @Mock
    lateinit var placeRegionRepository: EventPlaceRegionMongoRepository

    @Test
    fun 이벤트_일정_목록_조회_성공() {

        // given
        val year = 2026
        val month = 1
        val regionNo = null
        given(mongoOperations.find(any(), eq(EventDocument::class.java))).willReturn(listOf(eventDocumentFixture()))
        given(placeRepository.findByIdIn(anyList())).willReturn(listOf(placeDocumentFixture()))
        given(placeRegionRepository.findByRegionNoIn(anyList())).willReturn(listOf(placeRegionDocumentFixture()))

        // when
        val result = sut.getEvents(year, month, regionNo, null, null, null)

        // then
        assert(result.size == 1)
        assert(result[0].id == "event-1")
        assert(result[0].name == "토요일 미니리그")
        assert(result[0].regionName == "서울")
        assert(result[0].placeId == "place-1")
        assert(result[0].placeName == "포켓몬 카드샵 용산")
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
        given(placeRepository.findById(any())).willReturn(Optional.of(placeDocumentFixture()))
        given(placeRegionRepository.findByRegionNo(any())).willReturn(placeRegionDocumentFixture())

        // when
        val result = sut.getEventDetail(eventId)!!

        // then
        assert(result.id == "event-1")
        assert(result.name == "토요일 미니리그")
        assert(result.regionName == "서울")
        assert(result.placeId == "place-1")
        assert(result.placeName == "포켓몬 카드샵 용산")
        assert(result.category == LeagueCategoryType.OFFICIAL)
        assert(result.capacity == 64)
        assert(result.entryFee == 0L)

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
        given(placeRepository.findById(eventDocumentFixture().placeId)).willReturn(Optional.empty())

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
        given(placeRepository.findById(any())).willReturn(Optional.of(placeDocumentFixture()))
        given(placeRegionRepository.findByRegionNo(placeDocumentFixture().regionNo)).willReturn(null)

        // when
        val result = sut.getEventDetail(eventId)

        // then
        assert(result == null)

    }

    @Test
    fun 이벤트_일정_저장_성공() {

        // given
        val create = EventCreate(
            name = "test",
            placeId = "store-1",
            scheduledAt = LocalDateTime.now(),
            category = LeagueCategoryType.OFFICIAL,
            capacity = 64,
            entryFee = 0L,
            stages = listOf(EventStageCreate(stageNo = 1, type = StageType.SWISS, roundCount = 4, gameCount = 1))
        )

        // when
        sut.createEvent(create)

        // then
        then(eventRepository).should().save(any())

    }

    @Test
    fun 이벤트_일정_수정_성공() {

        // given
        val update = EventUpdate(
            id = "event-1",
            name = "test",
            placeId = "store-1",
            scheduledAt = LocalDateTime.now(),
            category = LeagueCategoryType.OFFICIAL,
            capacity = 64,
            entryFee = 0L,
            stages = listOf(EventStageUpdate(stageNo = 1, type = StageType.SWISS, roundCount = 4, gameCount = 1))
        )
        given(eventRepository.findById(update.id)).willReturn(Optional.of(eventDocumentFixture()))

        // when
        sut.updateEvent(update)

        // then
        then(eventRepository).should().save(any())

    }

    @Test
    fun 이벤트_일정_삭제_성공() {

        // given
        val eventId = "event-1"

        // when
        sut.deleteEvent(eventId)

        // then
        then(eventRepository).should().deleteById(eq(eventId))

    }

    @Test
    fun 과거_이벤트_여부_조회_성공() {

        // given
        val eventId = "event-1"
        given(eventRepository.findById(eventId)).willReturn(
            Optional.of(
                eventDocumentFixture(
                    scheduledAt = LocalDateTime.now().minusDays(1)
                )
            )
        )

        // when
        val result = sut.isPastEvent(eventId)

        // then
        assert(result)
        then(eventRepository).should().findById(eq(eventId))

    }

}