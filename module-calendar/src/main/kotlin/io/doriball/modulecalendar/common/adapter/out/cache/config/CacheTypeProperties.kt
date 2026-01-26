package io.doriball.modulecalendar.common.adapter.out.cache.config

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties(prefix = "doriball.management")
data class CacheTypeProperties(
    val cacheTtl: Map<String, Duration> = emptyMap(),
    val defaultTtl: Duration = Duration.ofHours(1),
) {
    fun ttlOf(cacheName: String): Duration =
        cacheTtl[cacheName] ?: defaultTtl
}