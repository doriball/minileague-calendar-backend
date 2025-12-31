package io.doriball.moduleadmin.store.application.port.`in`

import io.doriball.moduleadmin.store.application.dto.StoreRegionDto

interface StoreRegionUseCase {

    fun getStoreRegions(): List<StoreRegionDto>

}