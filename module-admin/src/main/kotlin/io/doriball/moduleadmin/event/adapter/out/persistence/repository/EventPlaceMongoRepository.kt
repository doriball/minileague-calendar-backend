package io.doriball.moduleadmin.event.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface EventPlaceMongoRepository : MongoRepository<PlaceDocument, String> {

    fun findByIdIn(placeIds: List<String>): List<PlaceDocument>

}