package io.doriball.moduleadmin.store.application.port.`in`.dto

data class ReadStoresCommand(
    val page: Int?,
    val size: Int?,
    val keyword: String?,
    val regionNo: Int?,
)
