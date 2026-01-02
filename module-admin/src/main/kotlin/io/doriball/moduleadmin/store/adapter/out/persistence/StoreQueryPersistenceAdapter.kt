package io.doriball.moduleadmin.store.adapter.out.persistence

import io.doriball.moduleadmin.store.adapter.out.persistence.repository.StoreEventMongoRepository
import io.doriball.moduleadmin.store.adapter.out.persistence.repository.StoreMongoRepository
import io.doriball.moduleadmin.store.adapter.out.persistence.repository.StoreRegionMongoRepository
import io.doriball.moduleadmin.store.application.port.out.StorePort
import io.doriball.moduleadmin.store.domain.StoreCreate
import io.doriball.moduleadmin.store.domain.StoreSummary
import io.doriball.moduleadmin.store.domain.StoreUpdate
import io.doriball.modulecore.domain.store.Store
import io.doriball.modulecore.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.domain.PageRequest
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class StoreQueryPersistenceAdapter(
    val mongoOperations: MongoOperations,
    val storeRepository: StoreMongoRepository,
    val storeRegionRepository: StoreRegionMongoRepository,
    val storeEventRepository: StoreEventMongoRepository,
) : StorePort {

    override fun getStores(
        page: Int?,
        size: Int?,
        keyword: String?,
        regionNo: Int?
    ): Pair<List<Store>, Long> {
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
        val total = mongoOperations.count(countQuery, StoreDocument::class.java)
        val stores = mongoOperations.find(query, StoreDocument::class.java).map { document ->
            val region = storeRegionRepository.findByRegionNo(document.regionNo)
                ?.let { DocumentConvertUtil.convertToStoreRegion(it) }
                ?: throw NotFoundException()
            DocumentConvertUtil.convertToStore(document, region)
        }

        return Pair(stores, total)
    }

    override fun getStoreSummaries(regionNo: Int): List<StoreSummary> {
        return storeRepository.findByRegionNo(regionNo).map { convertToStoreSummary(it) }
    }

    override fun getStoreDetail(storeId: String): Store? {
        val store = storeRepository.findByIdOrNull(storeId) ?: return null
        val storeRegion = storeRegionRepository.findByRegionNo(store.regionNo) ?: return null
        return DocumentConvertUtil.convertToStore(store, DocumentConvertUtil.convertToStoreRegion(storeRegion))
    }

    override fun createStore(create: StoreCreate) {
        val document = toStoreDocument(create)
        storeRepository.save(document)
    }

    override fun updateStore(storeId: String, update: StoreUpdate) {
        val document = toStoreDocument(update)
        storeRepository.save(document)
    }

    override fun deleteStore(storeId: String) {
        storeRepository.deleteById(storeId)
    }

    override fun isEventExist(storeId: String): Boolean = storeEventRepository.existsByStoreId(storeId)

    private fun convertToStoreSummary(store: StoreDocument): StoreSummary = StoreSummary(
        id = store.id!!, name = store.name
    )

    private fun toStoreDocument(create: StoreCreate): StoreDocument = StoreDocument(
        name = create.name,
        regionNo = create.regionNo,
        address = create.address,
        mapInformation = create.map,
        sns = create.sns,
    )

    private fun toStoreDocument(update: StoreUpdate): StoreDocument = StoreDocument(
        name = update.name,
        regionNo = update.regionNo,
        address = update.address,
        mapInformation = update.map,
        sns = update.sns,
    ).apply { id = update.id }

}