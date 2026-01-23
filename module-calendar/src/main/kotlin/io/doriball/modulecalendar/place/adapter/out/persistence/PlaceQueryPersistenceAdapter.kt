package io.doriball.modulecalendar.place.adapter.out.persistence

import io.doriball.modulecalendar.place.adapter.out.persistence.repository.PlaceMongoRepository
import io.doriball.modulecalendar.place.adapter.out.persistence.repository.PlaceRegionMongoRepository
import io.doriball.modulecalendar.place.application.port.out.PlacePort
import io.doriball.modulecore.domain.place.Place
import io.doriball.modulecore.enums.PlaceType
import io.doriball.modulecore.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PlaceQueryPersistenceAdapter(
    val placeRepository: PlaceMongoRepository,
    val placeRegionRepository: PlaceRegionMongoRepository
) : PlacePort {

    override fun getStorePlaces(regionNo: Int?): List<Place> {
        val stores = if (regionNo == null) placeRepository.findAllByType(PlaceType.STORE) else placeRepository.findByRegionNoAndType(regionNo, PlaceType.STORE)
        if (stores.isEmpty()) return emptyList()

        return if (regionNo == null) {
            val regionNos = stores.map { it.regionNo }.distinct()
            val regionMap = placeRegionRepository.findByRegionNoIn(regionNos)
                .associateBy { it.regionNo }

            stores.map { storeDocument ->
                val regionDocument = regionMap[storeDocument.regionNo] ?: throw NotFoundException()
                DocumentConvertUtil.convertToPlace(
                    storeDocument,
                    DocumentConvertUtil.convertToPlaceRegion(regionDocument)
                )
            }
        } else {
            val regionDocument = placeRegionRepository.findByRegionNo(regionNo) ?: return emptyList()
            val region = DocumentConvertUtil.convertToPlaceRegion(regionDocument)
            stores.map { DocumentConvertUtil.convertToPlace(it, region) }
        }
    }

    override fun getStorePlaceDetail(storeId: String): Place? {
        val store = placeRepository.findByIdOrNull(storeId) ?: return null
        val storeRegion = placeRegionRepository.findByRegionNo(store.regionNo) ?: return null
        return DocumentConvertUtil.convertToPlace(store, DocumentConvertUtil.convertToPlaceRegion(storeRegion))
    }
}