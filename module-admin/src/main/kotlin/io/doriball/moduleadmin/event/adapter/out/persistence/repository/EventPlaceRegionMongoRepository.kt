package io.doriball.moduleadmin.event.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.PlaceRegionDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface EventPlaceRegionMongoRepository: MongoRepository<PlaceRegionDocument, String> {

    fun findByRegionNo(regionNo: Int): PlaceRegionDocument?

    fun findByRegionNoIn(regionNos: List<Int>): List<PlaceRegionDocument>

}