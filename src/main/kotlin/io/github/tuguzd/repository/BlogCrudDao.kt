package io.github.tuguzd.repository

import io.github.tuguzd.model.Blog
import org.bson.types.ObjectId
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.*
import org.litote.kmongo.id.toId

class BlogCrudDao : KoinComponent {
    private val driver by inject<BlogDriver>()
    private val collection = driver.blogCollection

    fun create(blog: Blog): Id<Blog>? {
        collection.insertOne(blog)
        return blog.id
    }

    fun findById(id: String): Blog? =
        collection.findOne(Blog::id eq ObjectId(id).toId())

    fun updateById(id: String, request: Blog): Boolean =
        findById(id)?.let { blog ->
            collection.replaceOne(
                blog.copy(
                    name = request.name,
                    desc = request.desc,
                )
            ).modifiedCount == 1L
        } ?: false

    fun deleteById(id: String): Boolean =
        collection.deleteOneById(ObjectId(id)).deletedCount == 1L
}
