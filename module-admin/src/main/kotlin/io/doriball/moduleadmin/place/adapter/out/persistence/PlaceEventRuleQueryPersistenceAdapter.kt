package io.doriball.moduleadmin.place.adapter.out.persistence

import io.doriball.moduleadmin.place.adapter.out.persistence.repository.PlaceEventRuleMongoRepository
import io.doriball.moduleadmin.place.application.port.out.PlaceEventRulePort
import io.doriball.moduleadmin.place.domain.PlaceEventRuleCreate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleStageCreate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleStageUpdate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleUpdate
import io.doriball.modulecore.domain.place.PlaceEventRule
import io.doriball.modulecore.shared.exception.NotFoundException
import io.doriball.moduleinfrastructure.persistence.entity.StageDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceEventRuleDocument
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

@Repository
class PlaceEventRuleQueryPersistenceAdapter(val repository: PlaceEventRuleMongoRepository): PlaceEventRulePort {

    override fun getPlaceEventRules(placeId: String): List<PlaceEventRule> {
        val rules = repository.findByPlaceId(placeId)
        return rules.map { DocumentConvertUtil.convertToPlaceEventRule(it) }
    }

    override fun createPlaceEventRule(create: PlaceEventRuleCreate) {
        val document = toPlaceEventRuleDocument(create)
        repository.save(document)
    }

    override fun updatePlaceEventRule(update: PlaceEventRuleUpdate) {
        val place = repository.findByIdOrNull(update.id) ?: throw NotFoundException()
        place.apply {
            name = update.name
            dayOfWeek = update.dayOfWeek
            scheduledAt = update.scheduledAt
            category = update.category
            capacity = update.capacity
            entryFee = update.entryFee
            stages = update.stages.map { toStageDocument(it) }.toList()
        }
        repository.save(place)
    }

    override fun deletePlaceEventRule(placeId: String, ruleId: String) {
        repository.deleteByIdAndPlaceId(ruleId, placeId)
    }

    private fun toPlaceEventRuleDocument(create: PlaceEventRuleCreate): PlaceEventRuleDocument = PlaceEventRuleDocument(
        placeId = create.placeId,
        name = create.name,
        dayOfWeek = create.dayOfWeek,
        scheduledAt = create.scheduledAt,
        category = create.category,
        capacity = create.capacity,
        entryFee = create.entryFee,
        stages = create.stages.map { toStageDocument(it) }
    )

    private fun toStageDocument(create: PlaceEventRuleStageCreate): StageDocument = StageDocument(
        stageNo = create.stageNo,
        type = create.type.name,
        roundCount = create.roundCount,
        gameCountPerRound = create.gameCount
    )

    private fun toStageDocument(update: PlaceEventRuleStageUpdate): StageDocument = StageDocument(
        stageNo = update.stageNo,
        type = update.type.name,
        roundCount = update.roundCount,
        gameCountPerRound = update.gameCount
    )

}