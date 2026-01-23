package io.doriball.modulecalendar.place.application.port.`in`

import io.doriball.modulecalendar.place.application.dto.StoreDetailDto
import io.doriball.modulecalendar.place.application.dto.StoreDto
import io.doriball.modulecalendar.place.application.port.`in`.dto.ReadStoreDetailCommand
import io.doriball.modulecalendar.place.application.port.`in`.dto.ReadStoresCommand

interface PlaceUseCase {

    fun getStores(command: ReadStoresCommand): List<StoreDto>

    fun getStoreDetail(command: ReadStoreDetailCommand): StoreDetailDto

}