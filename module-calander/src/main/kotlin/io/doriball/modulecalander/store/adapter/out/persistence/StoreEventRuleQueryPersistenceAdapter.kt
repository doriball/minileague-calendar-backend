package io.doriball.modulecalander.store.adapter.out.persistence

import io.doriball.modulecalander.store.adapter.out.persistence.repository.StoreEventRuleMongoRepository
import io.doriball.modulecalander.store.application.port.out.StoreEventRulePort
import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.stereotype.Repository

@Repository
class StoreEventRuleQueryPersistenceAdapter(val repository: StoreEventRuleMongoRepository) : StoreEventRulePort {

    override fun getStoreEventRules(storeId: String): List<StoreEventRule> {
        return repository.findByStoreId(storeId).map { DocumentConvertUtil.convertToStoreEventRule(it) }
    }

}