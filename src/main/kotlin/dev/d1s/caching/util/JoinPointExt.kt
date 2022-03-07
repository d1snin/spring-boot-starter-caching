package dev.d1s.caching.util

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.reflect.MethodSignature

internal val JoinPoint.method get() = (signature as MethodSignature).method