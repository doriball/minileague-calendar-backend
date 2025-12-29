package io.doriball.modulecalander.store.application.service

import io.doriball.modulecalander.store.application.dto.StoreDetailDto
import io.doriball.modulecalander.store.application.dto.StoreDto
import io.doriball.modulecalander.store.application.port.`in`.StoreUseCase
import io.doriball.modulecalander.store.application.port.`in`.dto.ReadStoreDetailCommand
import io.doriball.modulecalander.store.application.port.`in`.dto.ReadStoresCommand
import io.doriball.modulecalander.store.application.port.out.StoreEventRulePort
import io.doriball.modulecalander.store.application.port.out.StorePort
import io.doriball.modulecore.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
class StoreServiceV1(val storePort: StorePort, val storeEventRulePort: StoreEventRulePort) : StoreUseCase {

    override fun getStores(command: ReadStoresCommand): List<StoreDto> {
        val stores = storePort.getStores(command.regionNo)
        return stores.map { StoreDto.of(it) }
    }

    override fun getStoreDetail(command: ReadStoreDetailCommand): StoreDetailDto {
        val store = storePort.getStoreDetail(command.storeId) ?: throw NotFoundException();
        return StoreDetailDto.of(store, storeEventRulePort.getStoreEventRules(store.id))
    }

}