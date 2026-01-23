package io.doriball.modulecalendar.place.application.dto

import io.doriball.modulecore.domain.place.Place

class StoreDto(
    val id: String,
    val name: String,
    val region: String,
    val address: String,
) {

    companion object {
        fun from(place: Place) =
            StoreDto(id = place.id, name = place.name, region = place.region.name, address = place.address)
    }

}