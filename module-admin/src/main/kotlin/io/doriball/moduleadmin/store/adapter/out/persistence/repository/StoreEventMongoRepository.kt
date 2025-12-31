package io.doriball.moduleadmin.store.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface StoreEventMongoRepository: MongoRepository<EventDocument, String> {

    fun existsByStoreId(storeId: String): Boolean

}