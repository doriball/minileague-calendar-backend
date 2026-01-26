package io.doriball.modulecalendar.common.adapter.out.cache.handler

import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.interceptor.CacheErrorHandler

class CustomCacheErrorHandler : CacheErrorHandler {

    private val log = LoggerFactory.getLogger(CustomCacheErrorHandler::class.java)

    override fun handleCacheGetError(
        exception: RuntimeException,
        cache: Cache,
        key: Any
    ) {
        log.error("get cache error at cache : ${cache.name}, key : $key, exception : ${exception.message}", exception)
    }

    override fun handleCachePutError(
        exception: RuntimeException,
        cache: Cache,
        key: Any,
        value: Any?
    ) {
        log.error("put cache error at cache : ${cache.name}, key : $key, exception : ${exception.message}", exception)
    }

    override fun handleCacheEvictError(
        exception: RuntimeException,
        cache: Cache,
        key: Any
    ) {
        log.error("evict cache error at cache : ${cache.name}, key : $key, exception : ${exception.message}", exception)
    }

    override fun handleCacheClearError(exception: RuntimeException, cache: Cache) {
        log.error("clear cache error at cache : ${cache.name}, exception : ${exception.message}", exception)
    }

}