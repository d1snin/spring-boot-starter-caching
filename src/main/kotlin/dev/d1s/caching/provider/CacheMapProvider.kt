package dev.d1s.caching.provider

public interface CacheMapProvider {

    public fun getCacheAsMap(cacheName: String): Map<Any, Any>
}