package dev.d1s.caching.aspect

import dev.d1s.caching.annotation.CachePutByIdProvider
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

@Aspect
internal class CachePutByIdProviderAspect {

    @Autowired
    private lateinit var cacheManager: CacheManager

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Pointcut("@annotation(dev.d1s.caching.annotation.CachePutByIdProvider)")
    private fun cachePutByIdProvider() {
    }

    @AfterReturning("cachePutByIdProvider()", returning = "returnValue")
    fun doCachePutting(joinPoint: JoinPoint, returnValue: Any?) {
        val method = (joinPoint.signature as MethodSignature).method
        val annotation = method.getAnnotation(CachePutByIdProvider::class.java)

        cacheManager.getCacheSafe(annotation.cacheName)
            .put(
                applicationContext.getBean(annotation.idProvider.java).getId(
                    TaggedValue(
                        returnValue ?: logAndAbort(method, true) {
                            return
                        }
                    )
                ),
                returnValue
            )
    }
}