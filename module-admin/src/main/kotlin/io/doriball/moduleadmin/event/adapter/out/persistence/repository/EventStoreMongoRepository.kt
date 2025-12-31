package io.doriball.moduleadmin.event.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface EventStoreMongoRepository : MongoRepository<StoreDocument, String> {

    fun findByIdIn(storeIds: List<String>): List<StoreDocument>

}