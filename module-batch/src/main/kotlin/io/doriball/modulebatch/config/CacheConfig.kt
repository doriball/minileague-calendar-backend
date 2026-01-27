package io.doriball.modulebatch.config

import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.cache.BatchStrategies
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory

@Profile("dev", "prod")
@EnableCaching
@Configuration
class CacheConfig : CachingConfigurer {

    @Bean
    fun redisCacheManager(connectionFactory: RedisConnectionFactory): RedisCacheManager =
        RedisCacheManager.RedisCacheManagerBuilder
            .fromCacheWriter(RedisCacheWriter
                    .nonLockingRedisCacheWriter(connectionFactory, BatchStrategies.scan(1000)))
            .build()

}