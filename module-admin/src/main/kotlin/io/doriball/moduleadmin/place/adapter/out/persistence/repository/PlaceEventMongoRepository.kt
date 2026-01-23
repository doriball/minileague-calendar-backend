package io.doriball.moduleadmin.place.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface PlaceEventMongoRepository: MongoRepository<EventDocument, String> {

    fun existsByPlaceId(placeId: String): Boolean

}