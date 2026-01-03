package io.doriball.modulecalendar.store.application.port.`in`

import io.doriball.modulecalendar.store.application.dto.StoreDetailDto
import io.doriball.modulecalendar.store.application.dto.StoreDto
import io.doriball.modulecalendar.store.application.port.`in`.dto.ReadStoreDetailCommand
import io.doriball.modulecalendar.store.application.port.`in`.dto.ReadStoresCommand

interface StoreUseCase {

    fun getStores(command: ReadStoresCommand): List<StoreDto>

    fun getStoreDetail(command: ReadStoreDetailCommand): StoreDetailDto

}