package io.doriball.modulecalander.store.adapter.out.persistence

import io.doriball.modulecalander.store.adapter.out.persistence.repository.StoreRegionMongoRepository
import io.doriball.modulecalander.store.application.port.out.StoreRegionPort
import io.doriball.modulecore.domain.store.StoreRegion
import io.doriball.moduleinfrastructure.persistence.entity.StoreRegionDocument
import org.springframework.stereotype.Repository

@Repository
class StoreRegionQueryPersistenceAdapter(val repository: StoreRegionMongoRepository): StoreRegionPort {

    override fun getStoreRegions(): List<StoreRegion> = repository.findAll().map { convertToStoreRegion(it) }

    private fun convertToStoreRegion(document: StoreRegionDocument) = StoreRegion(
        regionNo = document.regionNo,
        name = document.name
    )

}