package io.doriball.moduleadmin.store.domain

class StoreCreate(
    val name: String,
    val region: Int,
    val address: String,
    val map: String?,
    val sns: String?,
) {
}