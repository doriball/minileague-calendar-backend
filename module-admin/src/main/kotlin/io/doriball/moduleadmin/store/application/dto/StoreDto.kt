package io.doriball.moduleadmin.store.application.dto

import io.doriball.modulecore.domain.store.Store
import java.time.LocalDateTime

class StoreDto(
    val id: String,
    val name: String,
    val region: String,
    val address: String,
    val createdAt: LocalDateTime? = null,
) {

    companion object {
        fun from(store: Store) = StoreDto(
            id = store.id,
            name = store.name,
            region = store.region.name,
            address = store.address,
            createdAt = store.createdAt
        )
    }

}