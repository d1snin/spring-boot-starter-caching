package dev.d1s.caching.provider.id

import dev.d1s.caching.model.TaggedValue

public interface IdProvider {

    public fun getId(taggedValue: TaggedValue): String
}