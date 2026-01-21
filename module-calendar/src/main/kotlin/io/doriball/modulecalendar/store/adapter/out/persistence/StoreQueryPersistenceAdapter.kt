package io.doriball.modulecalendar.store.adapter.out.persistence

import io.doriball.modulecalendar.store.adapter.out.persistence.repository.StoreMongoRepository
import io.doriball.modulecalendar.store.adapter.out.persistence.repository.StoreRegionMongoRepository
import io.doriball.modulecalendar.store.application.port.out.StorePort
import io.doriball.modulecore.domain.store.Store
import io.doriball.modulecore.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class StoreQueryPersistenceAdapter(
    val storeRepository: StoreMongoRepository,
    val storeRegionRepository: StoreRegionMongoRepository
) : StorePort {

    override fun getStores(regionNo: Int?): List<Store> {
        val stores = if (regionNo == null) storeRepository.findAll() else storeRepository.findByRegionNo(regionNo)
        if (stores.isEmpty()) return emptyList()

        return if (regionNo == null) {
            val regionNos = stores.map { it.regionNo }.distinct()
            val regionMap = storeRegionRepository.findByRegionNoIn(regionNos)
                .associateBy { it.regionNo }

            stores.map { storeDocument ->
                val regionDocument = regionMap[storeDocument.regionNo] ?: throw NotFoundException()
                DocumentConvertUtil.convertToStore(
                    storeDocument,
                    DocumentConvertUtil.convertToStoreRegion(regionDocument)
                )
            }
        } else {
            val regionDocument = storeRegionRepository.findByRegionNo(regionNo) ?: return emptyList()
            val region = DocumentConvertUtil.convertToStoreRegion(regionDocument)
            stores.map { DocumentConvertUtil.convertToStore(it, region) }
        }
    }

    override fun getStoreDetail(storeId: String): Store? {
        val store = storeRepository.findByIdOrNull(storeId) ?: return null
        val storeRegion = storeRegionRepository.findByRegionNo(store.regionNo) ?: return null
        return DocumentConvertUtil.convertToStore(store, DocumentConvertUtil.convertToStoreRegion(storeRegion))
    }
}