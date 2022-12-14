@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.github.tuguzd.model.Blog
import io.github.tuguzd.model.toDto
import io.github.tuguzd.repository.BlogBusinessDao
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.business() {
    val dao by inject<BlogBusinessDao>()

    get<Blogs> {
        val blogList = dao.findAll().map(Blog::toDto)
        call.respond(blogList)
    }

    get<Blogs.SearchName> {
        val accountList = dao.findByName(it.name).map(Blog::toDto)
        call.respond(accountList)
    }

    get("/health") {
        call.respond("Healthy")
    }
}
