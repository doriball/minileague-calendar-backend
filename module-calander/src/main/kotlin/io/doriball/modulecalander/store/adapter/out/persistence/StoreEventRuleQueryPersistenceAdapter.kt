package io.doriball.modulecalander.store.adapter.out.persistence

import io.doriball.modulecalander.store.adapter.out.persistence.repository.StoreEventRuleMongoRepository
import io.doriball.modulecalander.store.application.port.out.StoreEventRulePort
import io.doriball.modulecore.domain.store.StoreEventRule
import io.doriball.modulecore.domain.store.StoreEventRuleStage
import io.doriball.modulecore.enums.StageType
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreEventRuleDocument
import org.springframework.stereotype.Repository

@Repository
class StoreEventRuleQueryPersistenceAdapter(val repository: StoreEventRuleMongoRepository) : StoreEventRulePort {

    override fun getStoreEventRules(storeId: String): List<StoreEventRule> {
        return repository.findByStoreId(storeId).map { convertToStoreEventRule(it) }
    }

    private fun convertToStoreEventRule(document: StoreEventRuleDocument) = StoreEventRule(
        id = document.id,
        storeId = document.storeId,
        name = document.name,
        dayOfWeek = document.dayOfWeek,
        scheduledAt = document.scheduledAt,
        official = document.official,
        stages = document.stages.map { convertToEventStage(it) }.toList(),
    )

    private fun convertToEventStage(document: StageDocument): StoreEventRuleStage =
        StoreEventRuleStage(
            stageNo = document.stageNo,
            type = StageType.valueOf(document.type),
            roundCount = document.roundCount,
            gameCountPerRound = document.gameCountPerRound
        )


}