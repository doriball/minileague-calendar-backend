package io.doriball.modulecalander.store.application.port.`in`

import io.doriball.modulecalander.store.application.dto.StoreRegionDto

interface StoreRegionUseCase {

    fun getStoreRegions(): List<StoreRegionDto>

}