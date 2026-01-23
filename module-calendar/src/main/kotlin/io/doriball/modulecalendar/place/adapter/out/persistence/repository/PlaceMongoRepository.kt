package io.doriball.modulecalendar.place.adapter.out.persistence.repository

import io.doriball.modulecore.enums.PlaceType
import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import org.springframework.data.mongodb.repository.MongoRepository

interface PlaceMongoRepository: MongoRepository<PlaceDocument, String> {

    fun findAllByType(type: PlaceType): List<PlaceDocument>

    fun findByRegionNoAndType(regionNo: Int, type: PlaceType): List<PlaceDocument>

}