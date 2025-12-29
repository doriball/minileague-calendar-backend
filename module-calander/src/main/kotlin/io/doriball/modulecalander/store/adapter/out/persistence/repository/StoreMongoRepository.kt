package io.doriball.modulecalander.store.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface StoreMongoRepository: MongoRepository<StoreDocument, String> {

    fun findByRegionNo(regionNo: Int): List<StoreDocument>

}