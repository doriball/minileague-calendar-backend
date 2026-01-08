package io.doriball.moduleadmin.store.adapter.out.persistence

import io.doriball.moduleadmin.store.adapter.out.persistence.repository.StoreEventRuleMongoRepository
import io.doriball.moduleadmin.store.application.port.out.StoreEventRulePort
import io.doriball.moduleadmin.store.domain.StoreEventRuleCreate
import io.doriball.moduleadmin.store.domain.StoreEventRuleStageCreate
import io.doriball.moduleadmin.store.domain.StoreEventRuleStageUpdate
import io.doriball.moduleadmin.store.domain.StoreEventRuleUpdate
import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreEventRuleDocument
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.stereotype.Repository

@Repository
class StoreEventRuleQueryPersistenceAdapter(val repository: StoreEventRuleMongoRepository): StoreEventRulePort {

    override fun getStoreEventRules(storeId: String): List<StoreEventRule> {
        val storeEventRules = repository.findByStoreId(storeId)
        return storeEventRules.map { DocumentConvertUtil.convertToStoreEventRule(it) }
    }

    override fun createStoreEventRule(create: StoreEventRuleCreate) {
        val document = toStoreEventRuleDocument(create)
        repository.save(document)
    }

    override fun updateStoreEventRule(update: StoreEventRuleUpdate) {
        val document = toStoreEventRuleDocument(update)
        repository.save(document)
    }

    override fun deleteStoreEventRule(storeId: String, ruleId: String) {
        repository.deleteByIdAndStoreId(ruleId, storeId)
    }

    private fun toStoreEventRuleDocument(create: StoreEventRuleCreate): StoreEventRuleDocument = StoreEventRuleDocument(
        storeId = create.storeId,
        name = create.name,
        dayOfWeek = create.dayOfWeek,
        scheduledAt = create.scheduledAt,
        category = create.category,
        capacity = create.capacity,
        entryFee = create.entryFee,
        stages = create.stages.map { toStageDocument(it) }
    )

    private fun toStageDocument(create: StoreEventRuleStageCreate): StageDocument = StageDocument(
        stageNo = create.stageNo,
        type = create.type.name,
        roundCount = create.roundCount,
        gameCountPerRound = create.gameCount
    )

    private fun toStoreEventRuleDocument(update: StoreEventRuleUpdate): StoreEventRuleDocument = StoreEventRuleDocument(
        storeId = update.storeId,
        name = update.name,
        dayOfWeek = update.dayOfWeek,
        scheduledAt = update.scheduledAt,
        category = update.category,
        capacity = update.capacity,
        entryFee = update.entryFee,
        stages = update.stages.map { toStageDocument(it) }
    ).apply { id = update.id }

    private fun toStageDocument(update: StoreEventRuleStageUpdate): StageDocument = StageDocument(
        stageNo = update.stageNo,
        type = update.type.name,
        roundCount = update.roundCount,
        gameCountPerRound = update.gameCount
    )

}