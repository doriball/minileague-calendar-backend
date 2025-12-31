package io.doriball.moduleadmin.store.application.port.`in`

import io.doriball.moduleadmin.store.application.dto.StoreDetailDto
import io.doriball.moduleadmin.store.application.dto.StoreDto
import io.doriball.moduleadmin.store.application.dto.StoreSummaryDto
import io.doriball.moduleadmin.store.application.port.`in`.dto.*

interface StoreUseCase {

    fun getStores(command: ReadStoresCommand): Pair<List<StoreDto>, Long>

    fun getStoreSummaries(command: ReadStoreSummariesCommand): List<StoreSummaryDto>

    fun getStoreDetail(command: ReadStoreDetailCommand): StoreDetailDto

    fun createStore(command: CreateStoreCommand)

    fun updateStore(storeId: String, command: UpdateStoreCommand)

    fun deleteStore(storeId: String)

}