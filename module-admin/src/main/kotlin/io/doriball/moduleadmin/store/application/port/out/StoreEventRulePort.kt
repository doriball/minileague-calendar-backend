package io.doriball.moduleadmin.store.application.port.out

import io.doriball.moduleadmin.store.domain.StoreEventRuleCreate
import io.doriball.moduleadmin.store.domain.StoreEventRuleUpdate
import io.doriball.modulecore.domain.store.StoreEventRule

interface StoreEventRulePort {

    fun getStoreEventRules(storeId: String): List<StoreEventRule>

    fun createStoreEventRule(create: StoreEventRuleCreate)

    fun updateStoreEventRule(update: StoreEventRuleUpdate)

    fun deleteStoreEventRule(storeId: String, ruleId: String)

}