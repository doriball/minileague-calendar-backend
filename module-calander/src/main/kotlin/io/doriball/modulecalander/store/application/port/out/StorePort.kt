package io.doriball.modulecalander.store.application.port.out

import io.doriball.modulecore.domain.store.Store

interface StorePort {

    fun getStores(regionId: Int?): List<Store>

    fun getStoreDetail(storeId: String): Store?

}