package io.doriball.moduleadmin.store.application.dto

import io.doriball.moduleadmin.store.domain.StoreSummary

class StoreSummaryDto(
    val id: String,
    val name: String,
) {

    companion object {
        fun from(store: StoreSummary) = StoreSummaryDto(
            id = store.id, name = store.name
        )
    }

}