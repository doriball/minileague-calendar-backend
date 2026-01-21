package io.doriball.modulecore.unit.domain

import io.doriball.modulecore.fixture.fixtureStoreEventRule
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StoreEventRuleUnitTest {

    @Test
    fun 상점_이벤트_스테이지_정보_조회_성공() {

        // given
        val storeEventRule = fixtureStoreEventRule()

        // when
        val stageTypes = storeEventRule.stageTypes
        val roundCount = storeEventRule.roundCount
        val gameCount = storeEventRule.gameCount

        // then
        assert(stageTypes.size == 1)
        assert(stageTypes[0] == "SWISS")
        assert(roundCount == 4)
        assert(gameCount == 1)

    }

}