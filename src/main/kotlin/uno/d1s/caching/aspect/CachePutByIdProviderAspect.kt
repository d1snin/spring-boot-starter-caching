/*
 * BSD 3-Clause License, Copyright (c) 2022, Mikhail Titov and other contributors.
 */

package uno.d1s.caching.aspect

import org.apache.logging.log4j.LogManager
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.CacheManager
import org.springframework.context.ApplicationContext
import uno.d1s.caching.annotation.aop.CachePutByIdProvider
import uno.d1s.caching.util.getCacheSafe

@Aspect
class CachePutByIdProviderAspect {

    @Autowired
    private lateinit var cacheManager: CacheManager

    @Autowired
    private lateinit var applicationContext: ApplicationContext

    private val logger = LogManager.getLogger()

    @Pointcut("@annotation(uno.d1s.pulseq.annotation.cache.CachePutByIdProvider)")
    private fun cachePutByIdProvider() {
    }

    @AfterReturning("cachePutByIdProvider()", returning = "returnValue")
    fun doCachePutting(joinPoint: JoinPoint, returnValue: Any?) {
        val method = (joinPoint.signature as MethodSignature).method
        val annotation = method.getAnnotation(CachePutByIdProvider::class.java)

        cacheManager.getCacheSafe(annotation.cacheName)
            .put(
                applicationContext.getBean(annotation.idProvider.java).getId(
                    returnValue
                        ?: logger.warn("Method ${method.name} returned the null value, aborting the caching process.")
                            .also {
                                return
                            }), returnValue
            )
    }
}