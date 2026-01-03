package io.doriball.modulecalendar.store.application.port.`in`

import io.doriball.modulecalendar.store.application.dto.StoreRegionDto

interface StoreRegionUseCase {

    fun getStoreRegions(): List<StoreRegionDto>

}