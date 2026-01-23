package io.doriball.modulecore.unit.domain

import io.doriball.modulecore.fixture.fixturePlaceEventRule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class PlaceEventRuleUnitTest {

    @Test
    fun 장소_이벤트_스테이지_정보_조회_성공() {

        // given
        val placeEventRule = fixturePlaceEventRule()

        // when
        val stageTypes = placeEventRule.stageTypes
        val roundCount = placeEventRule.roundCount
        val gameCount = placeEventRule.gameCount

        // then
        assert(stageTypes.size == 1)
        assert(stageTypes[0] == "SWISS")
        assert(roundCount == 4)
        assert(gameCount == 1)

    }

}