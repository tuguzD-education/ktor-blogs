package io.github.tuguzd.repository

import com.mongodb.client.model.IndexOptions
import io.github.tuguzd.model.Blog
import io.github.tuguzd.model.BlogDto
import org.litote.kmongo.KMongo
import org.litote.kmongo.ensureIndex
import org.litote.kmongo.getCollection

class BlogDriver(uri: String) {
    private val client = KMongo.createClient(uri)
    private val database = client.getDatabase("blog")
    val blogCollection = database.getCollection<Blog>()

    init {
        blogCollection.ensureIndex(
            properties = arrayOf(BlogDto::name),
            indexOptions = IndexOptions().unique(true)
        )
    }
}
