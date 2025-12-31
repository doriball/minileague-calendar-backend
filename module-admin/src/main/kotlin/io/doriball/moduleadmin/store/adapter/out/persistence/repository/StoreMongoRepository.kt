package io.doriball.moduleadmin.store.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface StoreMongoRepository: MongoRepository<StoreDocument, String> {
}