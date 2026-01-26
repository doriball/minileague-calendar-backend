package io.doriball.moduleadmin.place.adapter.out.persistence

import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceEventMongoRepository
import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceMongoRepository
import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceRegionMongoRepository
import io.doriball.moduleadmin.place.application.port.out.PlacePort
import io.doriball.moduleadmin.place.domain.PlaceCreate
import io.doriball.moduleadmin.place.domain.PlaceSummary
import io.doriball.moduleadmin.place.domain.PlaceUpdate
import io.doriball.modulecore.domain.place.Place
import io.doriball.modulecore.shared.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PlaceQueryPersistenceAdapter(
    val mongoOperations: MongoOperations,
    val placeRepository: PlaceMongoRepository,
    val placeRegionRepository: PlaceRegionMongoRepository,
    val placeEventRepository: PlaceEventMongoRepository,
) : PlacePort {

    override fun getPlaces(
        page: Int?,
        size: Int?,
        keyword: String?,
        regionNo: Int?
    ): Pair<List<Place>, Long> {
        val convertedPage = if (page == null || page <= 0) 0 else page - 1
        val pageable = PageRequest.of(convertedPage, size ?: 10)
        val query = Query().with(pageable)
        val criteria = mutableListOf<Criteria>()

        keyword?.takeIf { it.isNotBlank() }?.let {
            criteria.add(Criteria.where("name").regex(it, "i"))
        }

        regionNo?.let {
            criteria.add(Criteria.where("regionNo").`is`(it))
        }

        if (criteria.isNotEmpty()) {
            query.addCriteria(Criteria().andOperator(*criteria.toTypedArray()))
        }

        val countQuery = Query.of(query).limit(0).skip(0)
        val total = mongoOperations.count(countQuery, PlaceDocument::class.java)
        val placeDocuments = mongoOperations.find(query, PlaceDocument::class.java)
        val regionNos = placeDocuments.map { it.regionNo }.distinct()
        val regionMap = placeRegionRepository.findByRegionNoIn(regionNos).associateBy { it.regionNo }
        val places = placeDocuments.map { doc ->
            val regionDocument = regionMap[doc.regionNo] ?: throw NotFoundException()
            DocumentConvertUtil.convertToPlace(doc, DocumentConvertUtil.convertToPlaceRegion(regionDocument))
        }

        return Pair(places, total)
    }

    override fun getPlaceSummaries(regionNo: Int): List<PlaceSummary> {
        return placeRepository.findByRegionNo(regionNo).map { convertToPlaceSummary(it) }
    }

    override fun getPlaceDetail(placeId: String): Place? {
        val place = placeRepository.findByIdOrNull(placeId) ?: return null
        val region = placeRegionRepository.findByRegionNo(place.regionNo) ?: return null
        return DocumentConvertUtil.convertToPlace(place, DocumentConvertUtil.convertToPlaceRegion(region))
    }

    override fun createPlace(create: PlaceCreate) {
        val document = toPlaceDocument(create)
        placeRepository.save(document)
    }

    override fun updatePlace(placeId: String, update: PlaceUpdate) {
        val place = placeRepository.findByIdOrNull(placeId) ?: throw NotFoundException()
        place.apply {
            name = update.name
            regionNo = update.regionNo
            type = update.type
            address = update.address
            mapInformation = update.map
        }
        placeRepository.save(place)
    }

    override fun deletePlace(placeId: String) {
        placeRepository.deleteById(placeId)
    }

    override fun isEventExist(placeId: String): Boolean = placeEventRepository.existsByPlaceId(placeId)

    private fun convertToPlaceSummary(store: PlaceDocument): PlaceSummary = PlaceSummary(
        id = store.id!!, name = store.name
    )

    private fun toPlaceDocument(create: PlaceCreate): PlaceDocument = PlaceDocument(
        name = create.name,
        regionNo = create.regionNo,
        type = create.type,
        address = create.address,
        mapInformation = create.map,
        sns = create.sns,
    )

}