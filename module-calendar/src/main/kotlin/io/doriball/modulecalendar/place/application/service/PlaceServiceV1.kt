package io.doriball.modulecalendar.place.application.service

import io.doriball.modulecalendar.place.application.dto.StoreDetailDto
import io.doriball.modulecalendar.place.application.dto.StoreDto
import io.doriball.modulecalendar.place.application.port.`in`.PlaceUseCase
import io.doriball.modulecalendar.place.application.port.`in`.dto.ReadStoreDetailCommand
import io.doriball.modulecalendar.place.application.port.`in`.dto.ReadStoresCommand
import io.doriball.modulecalendar.place.application.port.out.PlaceEventRulePort
import io.doriball.modulecalendar.place.application.port.out.PlacePort
import io.doriball.modulecore.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class PlaceServiceV1(val placePort: PlacePort, val placeEventRulePort: PlaceEventRulePort) : PlaceUseCase {

    override fun getStores(command: ReadStoresCommand): List<StoreDto> {
        val stores = placePort.getStores(command.regionNo)
        return stores.map { StoreDto.from(it) }
    }

    override fun getStoreDetail(command: ReadStoreDetailCommand): StoreDetailDto {
        val store = placePort.getStoreDetail(command.storeId) ?: throw NotFoundException();
        return StoreDetailDto.from(store, placeEventRulePort.getPlaceEventRules(store.id))
    }

}