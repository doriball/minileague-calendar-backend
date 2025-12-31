package io.doriball.moduleadmin.store.adapter.out.persistence

import io.doriball.moduleadmin.store.adapter.out.persistence.repository.StoreRegionMongoRepository
import io.doriball.moduleadmin.store.application.port.out.StoreRegionPort
import io.doriball.modulecore.domain.store.StoreRegion
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.stereotype.Repository

@Repository
class StoreRegionQueryPersistenceAdapter(val repository: StoreRegionMongoRepository): StoreRegionPort {

    override fun getStoreRegions(): List<StoreRegion> {
        return repository.findAll().map { DocumentConvertUtil.convertToStoreRegion(it) }
    }

}