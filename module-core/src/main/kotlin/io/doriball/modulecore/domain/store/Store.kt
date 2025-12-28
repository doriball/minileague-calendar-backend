package io.doriball.modulecore.domain.store

import java.time.LocalDateTime

class Store(
    val id: String,
    val name: String,
    val region: StoreRegion,
    val address: String,
    val mapInformation: String?,
    val sns: String?,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {

    fun getRegionName(): String = region.name

}