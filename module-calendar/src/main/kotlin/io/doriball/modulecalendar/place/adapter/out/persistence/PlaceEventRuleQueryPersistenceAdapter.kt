package io.doriball.modulecalendar.place.adapter.out.persistence

import io.doriball.modulecalendar.place.adapter.out.persistence.repository.PlaceEventRuleMongoRepository
import io.doriball.modulecalendar.place.application.port.out.PlaceEventRulePort
import io.doriball.modulecore.domain.place.PlaceEventRule
import io.doriball.moduleinfrastructure.persistence.util.DocumentConvertUtil
import org.springframework.stereotype.Repository

@Repository
class PlaceEventRuleQueryPersistenceAdapter(val repository: PlaceEventRuleMongoRepository) : PlaceEventRulePort {

    override fun getPlaceEventRules(placeId: String): List<PlaceEventRule> {
        return repository.findByPlaceId(placeId).map { DocumentConvertUtil.convertToPlaceEventRule(it) }
    }

}