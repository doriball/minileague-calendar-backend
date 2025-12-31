package io.doriball.moduleadmin.store.application.service

import io.doriball.moduleadmin.store.application.dto.StoreEventRuleDto
import io.doriball.moduleadmin.store.application.port.`in`.StoreEventRuleUseCase
import io.doriball.moduleadmin.store.application.port.`in`.dto.CreateStoreEventRuleCommand
import io.doriball.moduleadmin.store.application.port.`in`.dto.ReadStoreEventRulesCommand
import io.doriball.moduleadmin.store.application.port.`in`.dto.UpdateStoreEventRuleCommand
import io.doriball.moduleadmin.store.application.port.out.StoreEventRulePort
import io.doriball.moduleadmin.store.domain.StoreEventRuleCreate
import io.doriball.moduleadmin.store.domain.StoreEventRuleUpdate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StoreEventRuleServiceV1(val storeEventRulePort: StoreEventRulePort): StoreEventRuleUseCase {

    override fun getStoreEventRules(command: ReadStoreEventRulesCommand): List<StoreEventRuleDto> {
        val rules = storeEventRulePort.getStoreEventRules(command.storeId)
        return rules.map { StoreEventRuleDto.from(it) }
    }

    @Transactional
    override fun createStoreEventRule(storeId: String, command: CreateStoreEventRuleCommand) {
        storeEventRulePort.createStoreEventRule(StoreEventRuleCreate.from(storeId, command))
    }

    @Transactional
    override fun updateStoreEventRule(storeId: String, ruleId: String, command: UpdateStoreEventRuleCommand) {
        storeEventRulePort.updateStoreEventRule(StoreEventRuleUpdate.from(storeId, ruleId, command))
    }

    @Transactional
    override fun deleteStoreEventRule(storeId: String, ruleId: String) {
        storeEventRulePort.deleteStoreEventRule(storeId, ruleId)
    }

}