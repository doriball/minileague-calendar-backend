package io.doriball.modulecalendar.place.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.PlaceRegionDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface PlaceRegionMongoRepository: MongoRepository<PlaceRegionDocument, String> {

    fun findByRegionNoIn(regionNos: List<Int>): List<PlaceRegionDocument>

    fun findByRegionNo(regionNo: Int): PlaceRegionDocument?

}