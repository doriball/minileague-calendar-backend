package io.doriball.moduleadmin.place.application.dto

import io.doriball.modulecore.domain.place.Place
import java.time.LocalDateTime

class PlaceDto(
    val id: String,
    val name: String,
    val region: String,
    val address: String,
    val createdAt: LocalDateTime?,
) {

    companion object {
        fun from(place: Place) = PlaceDto(
            id = place.id,
            name = place.name,
            region = place.region.name,
            address = place.address,
            createdAt = place.createdAt
        )
    }

}