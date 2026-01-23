package io.doriball.modulecore.unit.domain

import io.doriball.modulecore.fixture.fixturePlace
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PlaceUnitTest {

    @Test
    fun 장소_지역_정보_조회_성공() {

        // given
        val place = fixturePlace()

        // when
        val regionName = place.regionName

        // then
        assert("서울" == regionName)

    }

}