package dev.d1s.caching.autoconfiguration

import com.github.benmanes.caffeine.cache.Caffeine
import dev.d1s.caching.provider.CacheMapProvider
import dev.d1s.caching.provider.impl.CaffeineCacheMapProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnClass(Caffeine::class)
public class CaffeineAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    internal fun caffeine(): Caffeine<Any, Any> = Caffeine.newBuilder()
        .weakKeys()

    @Bean
    @ConditionalOnMissingBean
    internal fun cacheManager(): CacheManager = CaffeineCacheManager().apply {
        setCaffeine(caffeine())
    }

    @Bean
    @ConditionalOnMissingBean
    internal fun cacheMapProvider(): CacheMapProvider = CaffeineCacheMapProvider()
}