package io.doriball.moduleadmin.place.adapter.out.persistence

import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceRegionMongoRepository
import io.doriball.moduleadmin.place.application.port.out.PlaceRegionPort
import io.doriball.modulecore.domain.place.PlaceRegion
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.stereotype.Repository

@Repository
class PlaceRegionQueryPersistenceAdapter(private val repository: PlaceRegionMongoRepository): PlaceRegionPort {

    override fun getPlaceRegions(): List<PlaceRegion> {
        return repository.findAll().map { DocumentConvertUtil.convertToPlaceRegion(it) }
    }

}