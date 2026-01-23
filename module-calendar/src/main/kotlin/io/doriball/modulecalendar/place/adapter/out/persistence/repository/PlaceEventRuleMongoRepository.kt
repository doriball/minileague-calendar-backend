package io.doriball.modulecalendar.place.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.PlaceEventRuleDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface PlaceEventRuleMongoRepository : MongoRepository<PlaceEventRuleDocument, String> {

    fun findByPlaceId(placeId: String): List<PlaceEventRuleDocument>

}