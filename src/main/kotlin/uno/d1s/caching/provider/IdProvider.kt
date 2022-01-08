/*
 * BSD 3-Clause License, Copyright (c) 2022, Mikhail Titov and other contributors.
 */

package uno.d1s.caching.provider

interface IdProvider {

    fun getId(any: Any): String
}