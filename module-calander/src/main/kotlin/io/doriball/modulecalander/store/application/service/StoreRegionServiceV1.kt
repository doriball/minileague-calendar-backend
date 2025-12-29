package io.doriball.modulecalander.store.application.service

import io.doriball.modulecalander.store.application.dto.StoreRegionDto
import io.doriball.modulecalander.store.application.port.`in`.StoreRegionUseCase
import io.doriball.modulecalander.store.application.port.out.StoreRegionPort
import org.springframework.stereotype.Service

@Service
class StoreRegionServiceV1(val storeRegionPort: StoreRegionPort) : StoreRegionUseCase {

    override fun getStoreRegions(): List<StoreRegionDto> =
        storeRegionPort.getStoreRegions().map { StoreRegionDto.of(it) }

}