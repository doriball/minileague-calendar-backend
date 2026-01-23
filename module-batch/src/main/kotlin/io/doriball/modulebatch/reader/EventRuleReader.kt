package io.doriball.modulebatch.reader

import io.doriball.moduleinfrastructure.persistence.entity.PlaceDocument
import io.doriball.moduleinfrastructure.persistence.entity.PlaceEventRuleDocument
import org.bson.types.ObjectId
import org.springframework.batch.infrastructure.item.ExecutionContext
import org.springframework.batch.infrastructure.item.ItemStreamReader
import org.springframework.batch.infrastructure.item.data.MongoPagingItemReader
import org.springframework.batch.infrastructure.item.data.builder.MongoPagingItemReaderBuilder
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

open class EventRuleReader(
    private val chunkSize: Int,
    private val mongoTemplate: MongoTemplate,
) : ItemStreamReader<PlaceEventRuleDocument> {

    private lateinit var delegate: MongoPagingItemReader<PlaceEventRuleDocument>

    override fun open(executionContext: ExecutionContext) {
        val placeIds: List<String> =
            mongoTemplate.findDistinct(
                Query(),
                "_id",
                PlaceDocument::class.java,
                ObjectId::class.java
            ).map { it.toHexString() }
        val query = Query.query(Criteria.where("placeId").`in`(placeIds))

        delegate = MongoPagingItemReaderBuilder<PlaceEventRuleDocument>()
            .name("eventRulePagingReader")
            .template(mongoTemplate)
            .targetType(PlaceEventRuleDocument::class.java)
            .query(query)
            .parameterValues(mapOf<String, Any>("placeIds" to placeIds))
            .sorts(mapOf("_id" to Sort.Direction.ASC))
            .pageSize(chunkSize)
            .build()

        delegate.afterPropertiesSet()
        delegate.open(executionContext)
    }

    override fun read(): PlaceEventRuleDocument? = delegate.read()
    override fun update(executionContext: ExecutionContext) = delegate.update(executionContext)
    override fun close() = delegate.close()

}