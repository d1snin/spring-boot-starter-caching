package dev.d1s.caching.model

public data class TaggedValue(
    val value: Any,
    val tags: Set<String> = setOf()
)
