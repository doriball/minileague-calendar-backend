package io.doriball.modulecalendar.unit.store.application.service

import io.doriball.modulecalendar.fixture.domain.storeRegionFixture
import io.doriball.modulecalendar.store.application.port.out.StoreRegionPort
import io.doriball.modulecalendar.store.application.service.StoreRegionServiceV1
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StoreRegionServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: StoreRegionServiceV1

    @Mock
    lateinit var port: StoreRegionPort

    @Test
    fun 지역_목록_조회_성공() {

        // given
        given(port.getStoreRegions()).willReturn(listOf(storeRegionFixture()))

        // when
        val result = sut.getStoreRegions()

        // then
        assert(result.size == 1)
        assert(result[0].regionNo == 1)
        assert(result[0].name == "서울")

    }

}