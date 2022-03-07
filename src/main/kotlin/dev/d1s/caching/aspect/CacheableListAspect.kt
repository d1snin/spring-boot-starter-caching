package dev.d1s.caching.aspect

import dev.d1s.caching.annotation.CacheableList
import dev.d1s.caching.model.TaggedValue
import dev.d1s.caching.provider.CacheMapProvider
import dev.d1s.caching.util.getCacheSafe
import dev.d1s.caching.util.logAndAbort
import dev.d1s.teabag.stdlib.collection.anyIn
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotationUtils

@Aspect
internal class CacheableListAspect {

    @Autowired
    private lateinit var cacheManager: CacheManager

    @Autowired
    private lateinit var cacheMapProvider: CacheMapProvider

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Pointcut("@annotation(dev.d1s.caching.annotation.CacheableList)")
    private fun cacheableList() {
    }

    @Around("cacheableList()")
    fun doCaching(proceedingJoinPoint: ProceedingJoinPoint): Any? {
        val method = (proceedingJoinPoint.signature as MethodSignature).method
        val cacheableList = AnnotationUtils.getAnnotation(method, CacheableList::class.java)!!

        val cache = cacheManager.getCacheSafe(cacheableList.cacheName)
        val cacheMap = cacheMapProvider.getCacheAsMap(cacheableList.cacheName)

        return if (cacheMap.isEmpty()) {
            val returnedCollection = (proceedingJoinPoint.proceed()
                ?: logAndAbort(method, true) {
                    return null
                }) as Collection<*>

            returnedCollection.forEach {
                val taggedValue = TaggedValue(
                    it ?: throw IllegalArgumentException(
                        "Element of the collection could not be null."
                    ), setOf(*cacheableList.tags)
                )

                cache.put(
                    applicationContext.getBean(cacheableList.idProvider.java).getId(
                        taggedValue
                    ), taggedValue
                )
            }
            returnedCollection
        } else {
            cacheMap.values.map {
                if (it is TaggedValue) {
                    it
                } else {
                    throw IllegalStateException("Value type is not a TaggedValue: $cacheableList")
                }
            }.let { taggedValues ->
                (if (cacheableList.tags.isNotEmpty()) {
                    taggedValues.filter {
                        it.tags anyIn cacheableList.tags.asIterable()
                    }
                } else {
                    taggedValues
                }).map {
                    it.value
                }
            }
        }
    }
}