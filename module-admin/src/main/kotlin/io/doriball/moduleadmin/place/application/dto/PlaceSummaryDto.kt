package io.doriball.moduleadmin.place.application.dto

import io.doriball.moduleadmin.place.domain.PlaceSummary

class PlaceSummaryDto(
    val id: String,
    val name: String,
) {

    companion object {
        fun from(store: PlaceSummary) = PlaceSummaryDto(
            id = store.id, name = store.name
        )
    }

}