package io.doriball.moduleadmin.place.application.service

import io.doriball.moduleadmin.place.application.dto.PlaceEventRuleDto
import io.doriball.moduleadmin.place.application.port.`in`.PlaceEventRuleUseCase
import io.doriball.moduleadmin.place.application.port.`in`.dto.CreatePlaceEventRuleCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.ReadPlaceEventRulesCommand
import io.doriball.moduleadmin.place.application.port.`in`.dto.UpdatePlaceEventRuleCommand
import io.doriball.moduleadmin.place.application.port.out.PlaceEventRulePort
import io.doriball.moduleadmin.place.domain.PlaceEventRuleCreate
import io.doriball.moduleadmin.place.domain.PlaceEventRuleUpdate
import io.doriball.modulecore.shared.codes.SharedCacheName
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PlaceEventRuleServiceV1(private val placeEventRulePort: PlaceEventRulePort): PlaceEventRuleUseCase {

    override fun getPlaceEventRules(command: ReadPlaceEventRulesCommand): List<PlaceEventRuleDto> {
        val rules = placeEventRulePort.getPlaceEventRules(command.placeId)
        return rules.map { PlaceEventRuleDto.from(it) }
    }

    @Caching(
        evict = [
            CacheEvict(value = [SharedCacheName.EVENTS], allEntries = true),
            CacheEvict(value = [SharedCacheName.STORE_DETAIL], key = "#placeId")
        ]
    )
    @Transactional
    override fun createPlaceEventRule(placeId: String, command: CreatePlaceEventRuleCommand) {
        placeEventRulePort.createPlaceEventRule(PlaceEventRuleCreate.from(placeId, command))
    }

    @Caching(
        evict = [
            CacheEvict(value = [SharedCacheName.EVENTS], allEntries = true),
            CacheEvict(value = [SharedCacheName.STORE_DETAIL], key = "#placeId")
        ]
    )
    @Transactional
    override fun updatePlaceEventRule(placeId: String, ruleId: String, command: UpdatePlaceEventRuleCommand) {
        placeEventRulePort.updatePlaceEventRule(PlaceEventRuleUpdate.from(placeId, ruleId, command))
    }

    @Caching(
        evict = [
            CacheEvict(value = [SharedCacheName.EVENTS], allEntries = true),
            CacheEvict(value = [SharedCacheName.STORE_DETAIL], key = "#placeId")
        ]
    )
    @Transactional
    override fun deletePlaceEventRule(placeId: String, ruleId: String) {
        placeEventRulePort.deletePlaceEventRule(placeId, ruleId)
    }

}