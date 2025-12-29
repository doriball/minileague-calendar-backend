package io.doriball.modulecalander.event.adapter.out.persistence.repository

import io.doriball.moduleinfrastructure.persistence.entity.StoreRegionDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface EventStoreRegionMongoRepository : MongoRepository<StoreRegionDocument, String> {

    fun findByRegionNoIn(regionNos: List<Int>): List<StoreRegionDocument>

    fun findByRegionNo(regionNo: Int): StoreRegionDocument?

}