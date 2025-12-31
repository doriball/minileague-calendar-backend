package io.doriball.moduleadmin.store.application.port.`in`

import io.doriball.moduleadmin.store.application.dto.StoreEventRuleDto
import io.doriball.moduleadmin.store.application.port.`in`.dto.CreateStoreEventRuleCommand
import io.doriball.moduleadmin.store.application.port.`in`.dto.ReadStoreEventRulesCommand
import io.doriball.moduleadmin.store.application.port.`in`.dto.UpdateStoreEventRuleCommand

interface StoreEventRuleUseCase {

    fun getStoreEventRules(command: ReadStoreEventRulesCommand) : List<StoreEventRuleDto>

    fun createStoreEventRule(storeId: String, command: CreateStoreEventRuleCommand)

    fun updateStoreEventRule(storeId: String, ruleId: String, command:  UpdateStoreEventRuleCommand)

    fun deleteStoreEventRule(storeId: String, ruleId: String)

}