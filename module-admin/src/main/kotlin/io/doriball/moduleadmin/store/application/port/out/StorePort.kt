package io.doriball.moduleadmin.store.application.port.out

import io.doriball.moduleadmin.store.domain.StoreCreate
import io.doriball.moduleadmin.store.domain.StoreSummary
import io.doriball.moduleadmin.store.domain.StoreUpdate
import io.doriball.modulecore.domain.store.Store

interface StorePort {

    fun getStores(page: Int?, size: Int?, keyword: String?, regionNo: Int?): Pair<List<Store>, Long>

    fun getStoreSummaries(regionNo: Int): List<StoreSummary>

    fun getStoreDetail(storeId: String): Store?

    fun createStore(create: StoreCreate)

    fun updateStore(storeId: String, update: StoreUpdate)

    fun deleteStore(storeId: String)

    fun isEventExist(storeId: String): Boolean

}