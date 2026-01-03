package io.doriball.modulecalendar.store.application.dto

import io.doriball.modulecore.domain.store.StoreRegion

class StoreRegionDto(val regionNo: Int, val name: String) {
    companion object {
        fun of(storeRegion: StoreRegion) = StoreRegionDto(
            regionNo = storeRegion.regionNo, name = storeRegion.name
        )
    }
}