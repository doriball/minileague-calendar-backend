package io.doriball.modulebatch.processor

import io.doriball.modulebatch.dto.EventTimeKey
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceEventRuleDocument
import org.springframework.batch.infrastructure.item.ItemProcessor
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

open class EventGenerationProcessor(
    private val windowDays: Long,
    private val mongoTemplate: MongoTemplate,
) : ItemProcessor<PlaceEventRuleDocument, List<EventDocument>> {

    // TODO :: 추후 단순 Insert가 아닌 Update도 고려
    override fun process(item: PlaceEventRuleDocument): List<EventDocument>? {
        val today = LocalDate.now()
        val nDaysLater = today.plusDays(windowDays)

        val query = Query.query(
            Criteria
                .where("placeId").`is`(item.placeId)
                .and("scheduledAt").gte(today.atStartOfDay()).lte(nDaysLater.atTime(LocalTime.MAX))
        )

        val existEventMap: Map<String, Set<EventTimeKey>> =
            mongoTemplate.find(query, EventDocument::class.java).groupBy(
                { it.scheduledAt.dayOfWeek.name.substring(0, 3) },
                { EventTimeKey(it.name, it.scheduledAt.toLocalTime()) })
                .mapValues { it.value.toSet() }

        val itemDayOfWeek = item.dayOfWeek.name
        val existingKeys = existEventMap[itemDayOfWeek].orEmpty()
        val ruleKey = EventTimeKey(item.name, item.scheduledAt)

        var current = today
        val targetEvents = mutableListOf<EventDocument>()

        while (!current.isAfter(nDaysLater)) {
            if (itemDayOfWeek == current.dayOfWeek.name.substring(0, 3) && ruleKey !in existingKeys) {
                targetEvents.add(
                    EventDocument(
                        placeId = item.placeId,
                        name = item.name,
                        scheduledAt = LocalDateTime.of(current, item.scheduledAt),
                        category = item.category,
                        capacity = item.capacity,
                        entryFee = item.entryFee,
                        stages = item.stages,
                    )
                )
            }
            current = current.plusDays(1)
        }

        return targetEvents
    }

}