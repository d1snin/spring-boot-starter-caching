package dev.d1s.caching.annotation

import dev.d1s.caching.provider.id.IdListProvider
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class CacheEvictByListIdProvider(
    val cacheName: String,
    val idListProvider: KClass<out IdListProvider>
)

