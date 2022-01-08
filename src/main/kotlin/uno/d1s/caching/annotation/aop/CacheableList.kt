/*
 * BSD 3-Clause License, Copyright (c) 2022, Mikhail Titov and other contributors.
 */

package uno.d1s.caching.annotation.aop

import uno.d1s.caching.provider.IdProvider
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheableList(
    val cacheName: String,
    val idProvider: KClass<out IdProvider>
)
