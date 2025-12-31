package io.doriball.moduleadmin.store.application.dto

import io.doriball.modulecore.domain.store.Store

class StoreDetailDto(
    val id: String,
    val name: String,
    val region: String,
    val address: String,
    val map: String?,
    val sns: String?,
) {

    companion object {
        fun from(store: Store): StoreDetailDto = StoreDetailDto(
            id = store.id,
            name = store.name,
            region = store.region.name,
            address = store.address,
            map = store.mapInformation,
            sns = store.sns,
        )
    }

}