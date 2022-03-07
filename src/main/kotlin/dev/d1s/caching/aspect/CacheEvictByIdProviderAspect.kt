package dev.d1s.caching.aspect

import dev.d1s.caching.annotation.CacheEvictByIdProvider
import dev.d1s.caching.model.TaggedValue
import dev.d1s.caching.util.getCacheSafe
import dev.d1s.caching.util.logAndAbort
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.AnnotationUtils

@Aspect
internal class CacheEvictByIdProviderAspect {

    @Autowired
    private lateinit var cacheManager: CacheManager

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Pointcut("@annotation(dev.d1s.caching.annotation.CacheEvictByIdProvider)")
    private fun cacheEvictByIdProvider() {
    }

    @AfterReturning("cacheEvictByIdProvider()")
    fun doCacheEviction(joinPoint: JoinPoint, returnValue: Any?) {
        val method = (joinPoint.signature as MethodSignature).method
        val cacheEvictByIdProvider = AnnotationUtils.getAnnotation(method, CacheEvictByIdProvider::class.java)!!

        cacheManager.getCacheSafe(cacheEvictByIdProvider.cacheName).evict(
            applicationContext.getBean(cacheEvictByIdProvider.idProvider.java).getId(
                TaggedValue(
                    returnValue ?: logAndAbort(method, false) {
                        return
                    }
                )
            )
        )
    }
}