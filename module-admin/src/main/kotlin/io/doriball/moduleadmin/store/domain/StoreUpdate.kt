package io.doriball.moduleadmin.store.domain

import io.doriball.moduleadmin.store.application.port.`in`.dto.UpdateStoreCommand

class StoreUpdate(
    val id: String,
    val name: String,
    val regionNo: Int,
    val address: String,
    val map: String?,
    val sns: String?,
) {

    companion object {
        fun from(storeId: String, command: UpdateStoreCommand): StoreUpdate = StoreUpdate(
            id = storeId,
            name = command.name,
            regionNo = command.regionNo,
            address = command.address,
            map = command.map,
            sns = command.sns,
        )
    }

}