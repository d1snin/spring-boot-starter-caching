package dev.d1s.caching.annotation

import dev.d1s.caching.provider.id.IdProvider
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
public annotation class CacheEvictByIdProvider(
    val cacheName: String,
    val idProvider: KClass<out IdProvider>
)
