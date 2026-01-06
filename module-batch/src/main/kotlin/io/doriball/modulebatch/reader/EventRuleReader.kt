package io.doriball.modulebatch.reader

import io.doriball.moduleinfrastructure.persistence.entity.StoreDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreEventRuleDocument
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
) : ItemStreamReader<StoreEventRuleDocument> {

    private lateinit var delegate: MongoPagingItemReader<StoreEventRuleDocument>

    override fun open(executionContext: ExecutionContext) {
        val storeIds: List<String> =
            mongoTemplate.findDistinct(
                Query(),
                "_id",
                StoreDocument::class.java,
                ObjectId::class.java
            ).map { it.toHexString() }
        val query = Query.query(Criteria.where("storeId").`in`(storeIds))

        delegate = MongoPagingItemReaderBuilder<StoreEventRuleDocument>()
            .name("eventRulePagingReader")
            .template(mongoTemplate)
            .targetType(StoreEventRuleDocument::class.java)
            .query(query)
            .parameterValues(mapOf<String, Any>("storeIds" to storeIds))
            .sorts(mapOf("_id" to Sort.Direction.ASC))
            .pageSize(chunkSize)
            .build()

        delegate.afterPropertiesSet()
        delegate.open(executionContext)
    }

    override fun read(): StoreEventRuleDocument? = delegate.read()
    override fun update(executionContext: ExecutionContext) = delegate.update(executionContext)
    override fun close() = delegate.close()

}