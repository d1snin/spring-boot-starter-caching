package dev.d1s.caching.util

import dev.d1s.teabag.logging.logger
import org.slf4j.Logger
import java.lang.reflect.Method

private val logger: Logger = logger(
    Class.forName("dev.d1s.caching.util.CachingAbortingUtilKt").kotlin
)

internal inline fun logAndAbort(method: Method, caching: Boolean, returnExpression: () -> Unit) {
    logger.warn(
        "Method ${method.name} returned the null value, aborting the ${
            if (caching) {
                "caching"
            } else {
                "eviction"
            }
        } process."
    )

    returnExpression()
}