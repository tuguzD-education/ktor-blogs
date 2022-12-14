package io.github.tuguzd.repository

import io.github.tuguzd.model.Blog
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.regex

class BlogBusinessDao : KoinComponent {
    private val driver by inject<BlogDriver>()
    private val collection = driver.blogCollection

    fun findAll(): List<Blog> =
        collection.find().toList()

    fun findByName(name: String): List<Blog> =
        collection.find(
            (Blog::name).regex(name, "i")
        ).toList()
}
