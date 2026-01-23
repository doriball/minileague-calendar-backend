package io.doriball.moduleadmin.place.domain

import io.doriball.moduleadmin.place.application.port.`in`.dto.UpdatePlaceCommand
import io.doriball.modulecore.enums.PlaceType

class PlaceUpdate(
    val id: String,
    val name: String,
    val regionNo: Int,
    val type: PlaceType,
    val address: String,
    val map: String?,
    val sns: String?,
) {

    companion object {
        fun from(placeId: String, command: UpdatePlaceCommand): PlaceUpdate = PlaceUpdate(
            id = placeId,
            name = command.name,
            regionNo = command.regionNo,
            type = command.type,
            address = command.address,
            map = command.map,
            sns = command.sns,
        )
    }

}