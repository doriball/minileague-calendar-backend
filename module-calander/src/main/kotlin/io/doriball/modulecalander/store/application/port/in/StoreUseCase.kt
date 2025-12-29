package io.doriball.modulecalander.store.application.port.`in`

import io.doriball.modulecalander.store.application.dto.StoreDetailDto
import io.doriball.modulecalander.store.application.dto.StoreDto
import io.doriball.modulecalander.store.application.port.`in`.dto.ReadStoreDetailCommand
import io.doriball.modulecalander.store.application.port.`in`.dto.ReadStoresCommand

interface StoreUseCase {

    fun getStores(command: ReadStoresCommand): List<StoreDto>

    fun getStoreDetail(command: ReadStoreDetailCommand): StoreDetailDto

}