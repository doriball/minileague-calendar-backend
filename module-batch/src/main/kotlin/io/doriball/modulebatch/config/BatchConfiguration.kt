package io.doriball.modulebatch.config

import io.doriball.modulebatch.processor.EventGenerationProcessor
import io.doriball.modulebatch.reader.EventRuleReader
import io.doriball.modulebatch.writer.EventWriter
import io.doriball.moduleinfrastructure.persistence.entity.EventDocument
import io.doriball.moduleinfrastructure.persistence.entity.StoreEventRuleDocument
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.Job
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.parameters.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.Step
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.infrastructure.support.transaction.ResourcelessTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class BatchConfiguration(
    val mongoTemplate: MongoTemplate
) {

    companion object {
        const val CHUNK_SIZE = 100
        const val WINDOW_DAYS = 7L
    }

    // TODO :: 추후 ReplicaSet + Transaction 처리를 하게 된다면 MongoTransactionManager로 변경
    @Bean
    fun transactionManager(): PlatformTransactionManager {
        return ResourcelessTransactionManager()
    }

    @Bean
    fun eventCreateJob(
        jobRepository: JobRepository,
    ): Job = JobBuilder("eventCreateJob", jobRepository)
        .incrementer(RunIdIncrementer())
        .start(eventCreateStep(jobRepository, transactionManager()))
        .build()

    @Bean
    fun eventCreateStep(
        jobRepository: JobRepository,
        transactionManager: PlatformTransactionManager,
    ): Step = StepBuilder("eventCreateStep", jobRepository)
        .chunk<StoreEventRuleDocument, List<EventDocument>>(CHUNK_SIZE)
        .reader(eventRuleReader())
        .processor(eventGenerationProcessor())
        .writer(eventWriter())
        .transactionManager(transactionManager)
        .build()

    @Bean
    @StepScope
    fun eventRuleReader(): EventRuleReader = EventRuleReader(CHUNK_SIZE, mongoTemplate)

    @Bean
    @StepScope
    fun eventGenerationProcessor(): EventGenerationProcessor = EventGenerationProcessor(WINDOW_DAYS, mongoTemplate)

    @Bean
    @StepScope
    fun eventWriter(): EventWriter = EventWriter(mongoTemplate)

}