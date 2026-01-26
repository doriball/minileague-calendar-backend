package io.doriball.modulecalendar.unit.place.application.service

import io.doriball.modulecalendar.fixture.domain.placeEventRuleFixture
import io.doriball.modulecalendar.fixture.domain.placeFixture
import io.doriball.modulecalendar.place.application.port.`in`.dto.ReadStoreDetailCommand
import io.doriball.modulecalendar.place.application.port.`in`.dto.ReadStoresCommand
import io.doriball.modulecalendar.place.application.port.out.PlaceEventRulePort
import io.doriball.modulecalendar.place.application.port.out.PlacePort
import io.doriball.modulecalendar.place.application.service.PlaceServiceV1
import io.doriball.modulecore.domain.enums.DayOfWeekType
import io.doriball.modulecore.domain.enums.LeagueCategoryType
import io.doriball.modulecore.shared.exception.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class StoreServiceV1UnitTest {

    @InjectMocks
    lateinit var sut: PlaceServiceV1

    @Mock
    lateinit var placePort: PlacePort

    @Mock
    lateinit var placeEventRulePort: PlaceEventRulePort

    @Test
    fun 매장_목록_조회_성공() {

        // given
        val command = ReadStoresCommand(1)
        given(placePort.getStorePlaces(command.regionNo)).willReturn(listOf(placeFixture()))

        // when
        val result = sut.getStores(command)

        // then
        assert(result.size == 1)
        assert(result[0].id == "place-1")
        assert(result[0].name == "포켓몬 카드샵 용산")
        assert(result[0].region == "서울")

    }

    @Test
    fun 매장_상세_조회_성공() {

        // given
        val command = ReadStoreDetailCommand("place-1")
        given(placePort.getStorePlaceDetail(command.storeId)).willReturn(placeFixture())
        given(placeEventRulePort.getPlaceEventRules(command.storeId)).willReturn(listOf(placeEventRuleFixture()))

        // when
        val result = sut.getStoreDetail(command)

        // then
        assert(result.id == "place-1")
        assert(result.name == "포켓몬 카드샵 용산")
        assert(result.region == "서울")
        assert(result.events[DayOfWeekType.MON]!!.isEmpty())
        assert(result.events[DayOfWeekType.TUE]!!.isEmpty())
        assert(result.events[DayOfWeekType.WED]!!.isEmpty())
        assert(result.events[DayOfWeekType.THU]!!.isEmpty())
        assert(result.events[DayOfWeekType.FRI]!!.isEmpty())
        assert(result.events[DayOfWeekType.SAT]!!.size == 1)
        assert(result.events[DayOfWeekType.SAT]!![0].category == LeagueCategoryType.OFFICIAL)
        assert(result.events[DayOfWeekType.SAT]!![0].gameCount == 1)
        assert(result.events[DayOfWeekType.SAT]!![0].roundCount == 3)
        assert(result.events[DayOfWeekType.SAT]!![0].types.size == 1)
        assert(result.events[DayOfWeekType.SAT]!![0].types[0] == "SWISS")
        assert(result.events[DayOfWeekType.SUN]!!.isEmpty())

    }

    @Test
    fun 매장_상세_조회_실패_존재하지_않음() {

        // given
        val command = ReadStoreDetailCommand("place-2")
        given(placePort.getStorePlaceDetail(command.storeId)).willReturn(null)

        // when & then
        assertThrows<NotFoundException> {
            sut.getStoreDetail(command)
        }

    }

}