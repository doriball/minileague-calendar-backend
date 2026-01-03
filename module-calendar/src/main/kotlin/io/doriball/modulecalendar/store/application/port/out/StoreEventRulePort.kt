package io.doriball.modulecalendar.store.application.port.out

import io.doriball.modulecore.domain.store.StoreEventRule

interface StoreEventRulePort {

    fun getStoreEventRules(storeId: String): List<StoreEventRule>

}