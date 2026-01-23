package io.doriball.moduleadmin.place.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface PlaceMongoRepository: MongoRepository<PlaceDocument, String> {

    fun findByRegionNo(regionNo: Int): List<PlaceDocument>

}