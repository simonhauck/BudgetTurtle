package com.github.simonhauck.budgetturtle.server.core.db

import com.mongodb.client.FindIterable
import com.mongodb.client.model.Sorts
import kotlin.reflect.KProperty
import org.litote.kmongo.Id
import org.litote.kmongo.id.WrappedObjectId

fun <T> String.toObjectId(): Id<T> {
    return WrappedObjectId<T>(this)
}

fun <T> FindIterable<T>.sortByDescendingNestedProperties(
    vararg properties: KProperty<*>
): FindIterable<T> {
    return this.sort(Sorts.descending(properties.map { it.name }))
}
