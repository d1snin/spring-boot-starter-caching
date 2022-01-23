/*
 * BSD 3-Clause License, Copyright (c) 2022, Mikhail Titov and other contributors.
 */

package uno.d1s.caching.autoconfiguration

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.cache.CacheManager
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class CaffeineAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun caffeine(): Caffeine<Any, Any> = Caffeine.newBuilder()
        .weakKeys()

    @Bean
    @ConditionalOnMissingBean
    fun cacheManager(): CacheManager = CaffeineCacheManager().apply {
        setCaffeine(caffeine())
    }
}