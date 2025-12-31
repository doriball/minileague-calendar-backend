package io.doriball.moduleadmin.store.application.service

import io.doriball.moduleadmin.store.application.dto.StoreRegionDto
import io.doriball.moduleadmin.store.application.port.`in`.StoreRegionUseCase
import io.doriball.moduleadmin.store.application.port.out.StoreRegionPort
import org.springframework.stereotype.Service

@Service
class StoreRegionServiceV1(val port: StoreRegionPort): StoreRegionUseCase {

    override fun getStoreRegions(): List<StoreRegionDto> {
        return port.getStoreRegions().map { StoreRegionDto.from(it) }
    }

}