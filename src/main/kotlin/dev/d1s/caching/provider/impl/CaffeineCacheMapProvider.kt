package dev.d1s.caching.provider.impl

import com.github.benmanes.caffeine.cache.Cache
import dev.d1s.caching.provider.CacheMapProvider
import dev.d1s.caching.util.getCacheSafe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.caffeine.CaffeineCacheManager

internal class CaffeineCacheMapProvider : CacheMapProvider {

    @Autowired
    private lateinit var caffeineCacheManager: CaffeineCacheManager

    override fun getCacheAsMap(cacheName: String): Map<Any, Any> =
        @Suppress("UNCHECKED_CAST")
        (caffeineCacheManager.getCacheSafe(cacheName).nativeCache as Cache<Any, Any>).asMap()
}