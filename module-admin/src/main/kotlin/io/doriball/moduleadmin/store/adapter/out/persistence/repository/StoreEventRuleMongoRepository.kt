package io.doriball.moduleadmin.store.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.StoreEventRuleDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface StoreEventRuleMongoRepository: MongoRepository<StoreEventRuleDocument, String> {

    fun findByStoreId(storeId: String): List<StoreEventRuleDocument>

    fun deleteByIdAndStoreId(id: String, storeId: String)

}