package io.doriball.modulecalendar.common.adapter.out.cache.config

import io.doriball.modulecalendar.common.adapter.out.cache.handler.CustomCacheErrorHandler
import io.doriball.modulecore.shared.codes.SharedCacheName
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair.fromSerializer
import org.springframework.data.redis.serializer.RedisSerializer
import java.time.Duration

@Profile("dev", "prod")
@EnableCaching
@Configuration
@EnableConfigurationProperties(CacheTypeProperties::class)
class CacheConfig(
    private val properties: CacheTypeProperties
) : CachingConfigurer {

    @Bean
    fun redisCacheManager(
        redisConnectionFactory: RedisConnectionFactory,
    ): RedisCacheManager =
        RedisCacheManager.builder(redisConnectionFactory)
            .withInitialCacheConfigurations(createCaches())
            .build()

    private fun createCaches(): Map<String, RedisCacheConfiguration> {
        val cacheNames = listOf(
            SharedCacheName.REGIONS,
            SharedCacheName.EVENTS,
            SharedCacheName.STORES,
            SharedCacheName.STORE_DETAIL,
            SharedCacheName.NOTICES,
            SharedCacheName.NOTICE_DETAIL,
        )

        return cacheNames.associateWith { cacheName ->
            val ttl = properties.ttlOf(cacheName)
            createCache(ttl)
        }
    }

    private fun createCache(
        ttl: Duration,
    ): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(ttl)
            .serializeKeysWith(fromSerializer(RedisSerializer.string()))
            .serializeValuesWith(fromSerializer(RedisSerializer.json()))
            .disableCachingNullValues()
    }

    @Bean
    override fun errorHandler(): CacheErrorHandler = CustomCacheErrorHandler()

}