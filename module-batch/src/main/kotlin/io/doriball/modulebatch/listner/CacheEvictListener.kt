package io.doriball.modulebatch.listner

import io.doriball.modulecore.shared.codes.SharedCacheName
import org.slf4j.LoggerFactory
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.job.JobExecution
import org.springframework.batch.core.listener.JobExecutionListener
import org.springframework.data.redis.core.ScanOptions
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class CacheEvictListener(private val stringRedisTemplate: StringRedisTemplate) : JobExecutionListener {

    private val log = LoggerFactory.getLogger(CacheEvictListener::class.java)

    override fun afterJob(jobExecution: JobExecution) {
        if (jobExecution.status != BatchStatus.COMPLETED) return

        val pattern = "${SharedCacheName.EVENTS}::*"
        val deleted = deleteByScan(pattern)

        log.info("cache evicted. evict pattern={}, deletedCount={}", pattern, deleted)
    }

    // cache clear()를 해도 호출 시점 문제 때문에 삭제가 되지 않아 RedisTemplate으로 직접 삭제
    private fun deleteByScan(pattern: String): Long {
        val options = ScanOptions.scanOptions()
            .match(pattern)
            .count(1000)
            .build()

        var deleted = 0L
        stringRedisTemplate.scan(options).use { cursor ->
            val batch = ArrayList<String>(1000)

            while (cursor.hasNext()) {
                batch.add(cursor.next())

                if (batch.size >= 1000) {
                    deleted += stringRedisTemplate.delete(batch)
                    batch.clear()
                }
            }

            if (batch.isNotEmpty()) {
                deleted += stringRedisTemplate.delete(batch)
            }
        }

        return deleted
    }

}