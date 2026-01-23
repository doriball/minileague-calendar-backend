package io.doriball.modulecalendar.unit.place.application.service

import io.doriball.modulecalendar.fixture.domain.placeRegionFixture
import io.doriball.modulecalendar.place.application.port.out.PlaceRegionPort
import io.doriball.modulecalendar.place.application.service.PlaceRegionServiceV1
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StoreRegionServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: PlaceRegionServiceV1

    @Mock
    lateinit var port: PlaceRegionPort

    @Test
    fun 지역_목록_조회_성공() {

        // given
        given(port.getPlaceRegions()).willReturn(listOf(placeRegionFixture()))

        // when
        val result = sut.getPlaceRegions()

        // then
        assert(result.size == 1)
        assert(result[0].regionNo == 1)
        assert(result[0].name == "서울")

    }

}