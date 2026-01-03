package io.doriball.modulecalendar.store.application.dto

import io.doriball.modulecore.domain.store.Store

class StoreDto(
    val id: String,
    val name: String,
    val region: String,
    val address: String,
) {

    companion object {
        fun from(store: Store) =
            StoreDto(id = store.id, name = store.name, region = store.region.name, address = store.address)
    }

}