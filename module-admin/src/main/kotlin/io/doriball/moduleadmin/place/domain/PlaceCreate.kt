package io.doriball.moduleadmin.place.domain

import io.doriball.moduleadmin.place.application.port.`in`.dto.CreatePlaceCommand
import io.doriball.modulecore.domain.enums.PlaceType

class PlaceCreate(
    val name: String,
    val regionNo: Int,
    val type: PlaceType,
    val address: String,
    val map: String?,
    val sns: String?,
) {

    companion object {
        fun from(command: CreatePlaceCommand): PlaceCreate = PlaceCreate(
            name = command.name,
            regionNo = command.regionNo,
            type = command.type,
            address = command.address,
            map = command.map,
            sns = command.sns,
        )
    }

}