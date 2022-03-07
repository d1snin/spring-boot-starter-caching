package dev.d1s.caching.provider.id

import dev.d1s.caching.model.TaggedValue

public interface  IdListProvider {

    public fun getId(taggedValue: TaggedValue): List<String>
}