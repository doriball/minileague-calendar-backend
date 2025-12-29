package io.doriball.modulecalander.store.application.dto

import io.doriball.modulecore.domain.store.Store

class StoreDto(
    val id: String,
    val name: String,
    val region: String,
    val address: String,
) {

    companion object {
        fun of(store: Store) =
            StoreDto(id = store.id, name = store.name, region = store.region.name, address = store.address)
    }

}