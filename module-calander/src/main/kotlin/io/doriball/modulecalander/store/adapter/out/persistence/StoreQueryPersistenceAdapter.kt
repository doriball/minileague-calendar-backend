package io.doriball.modulecalander.store.adapter.out.persistence

import io.doriball.modulecalander.store.adapter.out.persistence.repository.StoreMongoRepository
import io.doriball.modulecalander.store.adapter.out.persistence.repository.StoreRegionMongoRepository
import io.doriball.modulecalander.store.application.port.out.StorePort
import io.doriball.modulecore.domain.store.Store
import io.doriball.modulecore.domain.store.StoreRegion
import io.doriball.modulecore.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreRegionDocument
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class StoreQueryPersistenceAdapter(
    val storeRepository: StoreMongoRepository,
    val storeRegionRepository: StoreRegionMongoRepository
) : StorePort {

    override fun getStores(regionId: Int?): List<Store> {
        val stores = if (regionId == null) storeRepository.findAll() else storeRepository.findByRegionNo(regionId)
        if (stores.isEmpty()) return emptyList()

        return if (regionId == null) {
            val regionNos = stores.map { it.regionNo }.distinct()
            val regionMap = storeRegionRepository.findByRegionNoIn(regionNos)
                .associateBy { it.regionNo }

            stores.map { storeDocument ->
                val regionDocument = regionMap[storeDocument.regionNo] ?: throw NotFoundException()
                convertToStore(storeDocument, convertToStoreRegion(regionDocument))
            }
        } else {
            val regionDocument = storeRegionRepository.findByRegionNo(regionId) ?: return emptyList()
            val region = convertToStoreRegion(regionDocument)
            stores.map { convertToStore(it, region) }
        }
    }

    override fun getStoreDetail(storeId: String): Store? {
        val store = storeRepository.findByIdOrNull(storeId) ?: return null
        val storeRegion = storeRegionRepository.findByRegionNo(store.regionNo) ?: return null
        return convertToStore(store, convertToStoreRegion(storeRegion))
    }

    private fun convertToStore(storeDocument: StoreDocument, storeRegion: StoreRegion) = Store(
        id = storeDocument.id,
        name = storeDocument.name,
        region = storeRegion,
        address = storeDocument.address,
        mapInformation = storeDocument.mapInformation,
        sns = storeDocument.sns,
        createdAt = storeDocument.createdAt,
        modifiedAt = storeDocument.modifiedAt
    )

    private fun convertToStoreRegion(storeRegionDocument: StoreRegionDocument) = StoreRegion(
        regionNo = storeRegionDocument.regionNo,
        name = storeRegionDocument.name
    )
}