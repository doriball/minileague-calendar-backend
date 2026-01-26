package io.doriball.moduleadmin.unit.place.application.service

import io.doriball.moduleadmin.fixture.domain.placeFixture
import io.doriball.moduleadmin.fixture.domain.placeSummaryFixture
import io.doriball.moduleadmin.place.application.port.`in`.dto.*
import io.doriball.moduleadmin.place.application.port.out.PlacePort
import io.doriball.moduleadmin.place.application.service.PlaceServiceV1
import io.doriball.moduleadmin.place.common.exception.EventExistException
import io.doriball.modulecore.domain.enums.PlaceType
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
import org.mockito.kotlin.eq

@ExtendWith(MockitoExtension::class)
class PlaceServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: PlaceServiceV1

    @Mock
    lateinit var port: PlacePort

    @Test
    fun 장소_목록_조회_성공() {

        // given
        val command = ReadPlacesCommand(1, 10, null, null)
        given(port.getPlaces(command.page, command.size, command.keyword, command.regionNo))
            .willReturn(listOf(placeFixture()) to 1L)

        // when
        val (result, size) = sut.getPlaces(command)

        // then
        assert(result.size == 1)
        assert(size == 1L)
        assert(result[0].id == "place-1")
        assert(result[0].name == "포켓몬 카드샵 용산")
        assert(result[0].region == "서울")

    }

    @Test
    fun 장소_요약_목록_조회_성공() {

        // given
        val command = ReadPlaceSummariesCommand(1)
        given(port.getPlaceSummaries(command.regionNo)).willReturn(listOf(placeSummaryFixture()))

        // when
        val result = sut.getPlaceSummaries(command)

        // then
        assert(result.size == 1)
        assert(result[0].name == "포켓몬 카드샵 용산")

    }

    @Test
    fun 장소_상세_조회_성공() {

        // given
        val command = ReadPlaceDetailCommand("place-1")
        given(port.getPlaceDetail(command.placeId)).willReturn(placeFixture())

        // when
        val result = sut.getPlaceDetail(command)

        // then
        assert(result.id == "place-1")
        assert(result.name == "포켓몬 카드샵 용산")
        assert(result.region == "서울")
        assert(result.type == PlaceType.STORE)

    }

    @Test
    fun 장소_상세_조회_실패_장소가_없음() {

        // given
        val command = ReadPlaceDetailCommand("place-2")
        given(port.getPlaceDetail(command.placeId)).willReturn(null)

        // when & then
        assertThrows<NotFoundException> {
            sut.getPlaceDetail(command)
        }

    }

    @Test
    fun 장소_등록_성공() {

        // given
        val command = CreatePlaceCommand(
            name = "test",
            regionNo = 1,
            type = PlaceType.STORE,
            address = "test",
            map = null,
            sns = null
        )

        // when
        sut.createPlace(command)

        // then
        then(port).should().createPlace(any())

    }

    @Test
    fun 장소_수정_성공() {

        // given
        val placeId = "place-1"
        val command = UpdatePlaceCommand(
            name = "test",
            regionNo = 1,
            type = PlaceType.STORE,
            address = "test",
            map = null,
            sns = null
        )

        // when
        sut.updatePlace(placeId, command)

        // then
        then(port).should().updatePlace(eq(placeId), any())

    }

    @Test
    fun 장소_삭제_성공() {

        // given
        val placeId = "place-1"
        given(port.isEventExist(placeId)).willReturn(false)

        // when
        sut.deletePlace(placeId)

        // then
        then(port).should().deletePlace(placeId)

    }

    @Test
    fun 장소_삭제_실패_이벤트_일정이_존재함() {

        // given
        val placeId = "place-1"
        given(port.isEventExist(placeId)).willReturn(true)

        // when & then
        assertThrows<EventExistException> {
            sut.deletePlace(placeId)
        }

    }

}