package com.github.simonhauck.budgetturtle.server.core.db

import org.litote.kmongo.Id
import org.litote.kmongo.id.WrappedObjectId

fun <T> String.toObjectId(): Id<T> {
    return WrappedObjectId<T>(this)
}
