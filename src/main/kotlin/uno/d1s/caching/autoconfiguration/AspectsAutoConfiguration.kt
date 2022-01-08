package uno.d1s.caching.autoconfiguration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uno.d1s.caching.aspect.CacheEvictByIdProviderAspect
import uno.d1s.caching.aspect.CacheEvictByListIdProviderAspect
import uno.d1s.caching.aspect.CachePutByIdProviderAspect
import uno.d1s.caching.aspect.CacheableListAspect

@Configuration(proxyBeanMethods = false)
class AspectsAutoConfiguration {

    @Bean
    fun cacheableListAspect() = CacheableListAspect()

    @Bean
    fun cacheEvictByIdProviderAspect() = CacheEvictByIdProviderAspect()

    @Bean
    fun cacheEvictByListIdProviderAspect() = CacheEvictByListIdProviderAspect()

    @Bean
    fun cachePutByIdProviderAspect() = CachePutByIdProviderAspect()
}