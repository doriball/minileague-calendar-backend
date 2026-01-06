package io.doriball.modulebatch.writer

import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import org.springframework.batch.infrastructure.item.Chunk
import org.springframework.batch.infrastructure.item.ItemWriter
import org.springframework.data.mongodb.core.MongoTemplate

open class EventWriter(private val mongoTemplate: MongoTemplate): ItemWriter<List<EventDocument>> {

    override fun write(chunk: Chunk<out List<EventDocument>>) {
        val allEvents = chunk.items.flatten()
        if (allEvents.isNotEmpty()) {
            mongoTemplate.insertAll(allEvents)
        }
    }

}