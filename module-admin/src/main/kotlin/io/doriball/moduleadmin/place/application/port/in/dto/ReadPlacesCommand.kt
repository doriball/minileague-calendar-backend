package io.doriball.moduleadmin.place.application.port.`in`.dto

data class ReadPlacesCommand(
    val page: Int?,
    val size: Int?,
    val keyword: String?,
    val regionNo: Int?,
)
