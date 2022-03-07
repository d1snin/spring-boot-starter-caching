package dev.d1s.caching.autoconfiguration

import dev.d1s.caching.aspect.CacheEvictByIdProviderAspect
import dev.d1s.caching.aspect.CacheEvictByListIdProviderAspect
import dev.d1s.caching.aspect.CachePutByIdProviderAspect
import dev.d1s.caching.aspect.CacheableListAspect
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
public class AspectAutoConfiguration {

    @Bean
    internal fun cacheableListAspect() = CacheableListAspect()

    @Bean
    internal fun cacheEvictByIdProviderAspect() = CacheEvictByIdProviderAspect()

    @Bean
    internal fun cacheEvictByListIdProviderAspect() = CacheEvictByListIdProviderAspect()

    @Bean
    internal fun cachePutByIdProviderAspect() = CachePutByIdProviderAspect()
}