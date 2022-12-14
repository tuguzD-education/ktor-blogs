@file:OptIn(KtorExperimentalLocationsAPI::class)

package io.github.tuguzd.route

import io.github.tuguzd.model.*
import io.github.tuguzd.repository.BlogCrudDao
import io.github.tuguzd.model.toDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.crud() {
    val dao by inject<BlogCrudDao>()

    post<Blogs.Create> {
        val request = call.receive<BlogDto>()
        val blog = request.toBlog()

        dao.create(blog)?.let { id ->
            call.apply {
                response.headers.append("My-User-Id-Header", id.toString())
                respond(HttpStatusCode.Created)
            }
        } ?: call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
    }

    put<Blogs.Update> {
        val request = call.receive<BlogDto>()
        val blog = request.toBlog()

        if (dao.updateById(it.id, blog)) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.BadRequest, ErrorResponse.BAD_REQUEST_RESPONSE)
    }

    get<Blogs.Read> {
        dao.findById(it.id)
            ?.let { blog -> call.respond(blog.toDto()) }
            ?: call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
    }

    delete<Blogs.Delete> {
        if (dao.deleteById(it.id)) call.respond(HttpStatusCode.NoContent)
        else call.respond(HttpStatusCode.NotFound, ErrorResponse.NOT_FOUND_RESPONSE)
    }
}
