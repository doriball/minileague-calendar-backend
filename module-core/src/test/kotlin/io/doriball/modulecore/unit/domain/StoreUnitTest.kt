package io.doriball.modulecore.unit.domain

import io.doriball.modulecore.fixture.fixtureStore
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StoreUnitTest {

    @Test
    fun 상점_지역_정보_조회_성공() {

        // given
        val store = fixtureStore()

        // when
        val regionName = store.regionName

        // then
        assert("서울" == regionName)

    }

}