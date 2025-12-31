package io.doriball.moduleadmin.store.application.service

import io.doriball.moduleadmin.store.application.dto.StoreDetailDto
import io.doriball.moduleadmin.store.application.dto.StoreDto
import io.doriball.moduleadmin.store.application.dto.StoreSummaryDto
import io.doriball.moduleadmin.store.application.port.`in`.StoreUseCase
import io.doriball.moduleadmin.store.application.port.`in`.dto.*
import io.doriball.moduleadmin.store.application.port.out.StorePort
import io.doriball.moduleadmin.store.common.exception.EventExistException
import io.doriball.moduleadmin.store.domain.StoreCreate
import io.doriball.moduleadmin.store.domain.StoreUpdate
import io.doriball.modulecore.exception.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoreServiceV1(val storePort: StorePort) : StoreUseCase {

    override fun getStores(command: ReadStoresCommand): Pair<List<StoreDto>, Long> {
        val (stores, size) = storePort.getStores(
            page = command.page,
            size = command.size,
            keyword = command.keyword,
            regionNo = command.regionNo
        )
        return stores.map { StoreDto.from(it) } to size
    }

    override fun getStoreSummaries(command: ReadStoreSummariesCommand): List<StoreSummaryDto> {
        return storePort.getStoreSummaries(command.regionNo).map { StoreSummaryDto.from(it) }
    }

    override fun getStoreDetail(command: ReadStoreDetailCommand): StoreDetailDto {
        val store = storePort.getStoreDetail(command.storeId) ?: throw NotFoundException()
        return StoreDetailDto.from(store)
    }

    @Transactional
    override fun createStore(command: CreateStoreCommand) {
        storePort.createStore(StoreCreate.from(command))
    }

    @Transactional
    override fun updateStore(storeId: String, command: UpdateStoreCommand) {
        storePort.updateStore(storeId, StoreUpdate.from(storeId, command))
    }

    @Transactional
    override fun deleteStore(storeId: String) {
        if (storePort.isEventExist(storeId)) throw EventExistException()
        storePort.deleteStore(storeId)
    }

}