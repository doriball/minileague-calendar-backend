package io.doriball.modulecalendar.event.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface EventPlaceMongoRepository : MongoRepository<PlaceDocument, String> {

    fun findByRegionNo(regionNo: Int): List<PlaceDocument>

    fun findByIdIn(storeIds: List<String>): List<PlaceDocument>

}