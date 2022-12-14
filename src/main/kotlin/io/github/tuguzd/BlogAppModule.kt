@file:Suppress("unused", "UnnecessaryOptInAnnotation")

package io.github.tuguzd

import io.github.tuguzd.repository.*
import io.github.tuguzd.route.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

@OptIn(KtorExperimentalLocationsAPI::class)
fun Application.module() {
    val message = "Welcome to Gelf Logging to Seq"
    log.info(message)

    val databaseUri = environment.config.property(
        "database.connectionUri"
    ).getString()
    val appModule = module {
        single { BlogDriver(databaseUri) }
        singleOf(::BlogCrudDao)
        singleOf(::BlogBusinessDao)
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    install(ContentNegotiation) {
        json()
    }
    install(Locations)

    routing {
        crud()
        business()
    }
}
