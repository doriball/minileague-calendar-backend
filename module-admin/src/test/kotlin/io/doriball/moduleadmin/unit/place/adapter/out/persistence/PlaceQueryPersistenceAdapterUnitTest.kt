package io.doriball.moduleadmin.unit.place.adapter.out.persistence

import io.doriball.moduleadmin.fixture.document.placeDocumentFixture
import io.doriball.moduleadmin.fixture.document.placeRegionDocumentFixture
import io.doriball.moduleadmin.place.adapter.out.persistence.PlaceQueryPersistenceAdapter
import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceEventMongoRepository
import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceMongoRepository
import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceRegionMongoRepository
import io.doriball.moduleadmin.place.domain.PlaceCreate
import io.doriball.moduleadmin.place.domain.PlaceUpdate
import io.doriball.modulecore.domain.enums.PlaceType
import io.doriball.modulecore.shared.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
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
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class PlaceQueryPersistenceAdapterUnitTest {

    @InjectMocks
    lateinit var sut: PlaceQueryPersistenceAdapter

    @Mock
    lateinit var mongoOperations: MongoOperations

    @Mock
    lateinit var placeRepository: PlaceMongoRepository

    @Mock
    lateinit var placeRegionRepository: PlaceRegionMongoRepository

    @Mock
    lateinit var placeEventRepository: PlaceEventMongoRepository

    @Test
    fun 장소_목록_조회_성공() {

        // given
        val page = 1
        val size = 10
        given(mongoOperations.find(any(), eq(PlaceDocument::class.java))).willReturn(listOf(placeDocumentFixture()))
        given(mongoOperations.count(any(), eq(PlaceDocument::class.java))).willReturn(1L)
        given(placeRegionRepository.findByRegionNoIn(anyList())).willReturn(listOf(placeRegionDocumentFixture()))

        // when
        val (result, count) = sut.getPlaces(page, size, null, null)

        // then
        assert(result.size == 1)
        assert(count == 1L)
        assert(result[0].id == "place-1")
        assert(result[0].name == "포켓몬 카드샵 용산")
        assert(result[0].regionName == "서울")
        assert(result[0].type == PlaceType.STORE)

    }

    @Test
    fun 장소_요약_목록_조회_성공() {

        // given
        val regionNo = 1
        given(placeRepository.findByRegionNo(regionNo)).willReturn(listOf(placeDocumentFixture()))

        // when
        val result = sut.getPlaceSummaries(regionNo)

        // then
        assert(result.size == 1)
        assert(result[0].id == "place-1")
        assert(result[0].name == "포켓몬 카드샵 용산")

    }

    @Test
    fun 장소_단건_조회_성공() {

        // given
        val placeId = "place-1"
        given(placeRepository.findById(placeId)).willReturn(Optional.of(placeDocumentFixture()))
        given(placeRegionRepository.findByRegionNo(placeDocumentFixture().regionNo)).willReturn(placeRegionDocumentFixture())

        // when
        val result = sut.getPlaceDetail(placeId)!!

        // then
        assert(result.id == "place-1")
        assert(result.name == "포켓몬 카드샵 용산")
        assert(result.regionName == "서울")
        assert(result.type == PlaceType.STORE)

    }

    @Test
    fun 장소_단건_조회_NULL_장소가_없는_경우() {

        // given
        val placeId = "place-2"
        given(placeRepository.findById(placeId)).willReturn(Optional.empty())

        // when
        val result = sut.getPlaceDetail(placeId)

        // then
        assert(result == null)

    }

    @Test
    fun 장소_단건_조회_NULL_지역이_없는_경우() {

        // given
        val placeId = "place-1"
        given(placeRepository.findById(placeId)).willReturn(Optional.of(placeDocumentFixture()))
        given(placeRegionRepository.findByRegionNo(placeDocumentFixture().regionNo)).willReturn(null)

        // when
        val result = sut.getPlaceDetail(placeId)

        // then
        assert(result == null)

    }

    @Test
    fun 장소_저장_성공() {

        // given
        val create = PlaceCreate(
            name = "test",
            regionNo = 1,
            type = PlaceType.STORE,
            address = "test",
            map = null,
            sns = null
        )

        // when
        sut.createPlace(create)

        // then
        then(placeRepository).should().save(any())

    }

    @Test
    fun 장소_수정_성공() {

        // given
        val placeId = "place-1"
        val update = PlaceUpdate(
            id = placeId,
            name = "test",
            regionNo = 1,
            type = PlaceType.STORE,
            address = "test",
            map = null,
            sns = null
        )
        given(placeRepository.findById(placeId)).willReturn(Optional.of(placeDocumentFixture()))

        // when
        sut.updatePlace(placeId, update)

        // then
        then(placeRepository).should().save(any())

    }

    @Test
    fun 장소_수정_실패_장소가_없는_경우() {

        // given
        val placeId = "place-2"
        val update = PlaceUpdate(
            id = placeId,
            name = "test",
            regionNo = 1,
            type = PlaceType.STORE,
            address = "test",
            map = null,
            sns = null
        )
        given(placeRepository.findById(placeId)).willReturn(Optional.empty())

        // when & then
        assertThrows<NotFoundException> {
            sut.updatePlace(placeId, update)
        }

    }

    @Test
    fun 장소_삭제_성공() {

        // given
        val placeId = "place-1"

        // when
        sut.deletePlace(placeId)

        // then
        then(placeRepository).should().deleteById(eq(placeId))

    }

    @Test
    fun 이벤트_일정_존재_여부_조회_성공() {

        // given
        val placeId = "place-1"

        // when
        sut.isEventExist(placeId)

        // then
        then(placeEventRepository).should().existsByPlaceId(placeId)

    }

}