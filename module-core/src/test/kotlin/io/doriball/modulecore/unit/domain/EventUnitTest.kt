package io.doriball.modulecore.unit.domain

import io.doriball.modulecore.fixture.fixtureEvent
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class EventUnitTest {

    @Test
    fun 이벤트_매장_정보_조회_성공() {

        // given
        val event = fixtureEvent()

        // when
        val placeId = event.placeId
        val storeName = event.placeName
        val regionName = event.regionName

        // then
        assert(placeId == "1")
        assert(storeName == "포켓몬 카드샵 용산")
        assert(regionName == "서울")

    }

    @Test
    fun 이벤트_스테이지_정보_조회_성공() {

        // given
        val event = fixtureEvent()

        // when
        val stageTypes = event.stageTypes
        val roundCount = event.roundCount
        val gameCount = event.gameCount

        // then
        assert(stageTypes.size == 1)
        assert(stageTypes[0] == "SWISS")
        assert(roundCount == 4)
        assert(gameCount == 1)

    }

}
