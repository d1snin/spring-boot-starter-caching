[![](https://jitpack.io/v/d1snin/spring-boot-starter-caching.svg)](https://jitpack.io/#d1snin/spring-boot-starter-caching)
# spring-boot-starter-caching
Spring Cache extensions with default Caffeine backend. Built for my projects. Allows you to cache the data using custom ID providers.
### Installation
```kotlin
repositories {
    maven(url = "https://jitpack.io/")
}

dependencies {
    implementation("dev.d1s:spring-boot-starter-caching:$springBootStarterCachingVersion")
    
    // add the caffeine cache
    implementation("com.github.ben-manes.caffeine:caffeine:$caffeineVersion")
}
```
