package de.stuebingerb

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.http.formUrlEncode
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.oauth
import io.ktor.server.request.contentType
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testWithOauth() = testApplication {
        application {
            install(Authentication) {
                oauth("auth-oauth-dummy") {
                    urlProvider = { "http://localhost:8080/callback" }
                    providerLookup = {
                        OAuthServerSettings.OAuth2ServerSettings(
                            name = "dummy",
                            authorizeUrl = "localhost",
                            accessTokenUrl = "localhost",
                            clientId = "clientId",
                            clientSecret = "clientSecret"
                        )
                    }
                    client = HttpClient(Apache)
                }
            }

            routing {
                route("/") {
                    get {
                        call.respond("GET /")
                    }
                    post {
                        val contentType = call.request.contentType()
                        val payload = call.receiveText()
                        call.respond("POST / - $contentType - $payload")
                    }
                }
                route("/oauth") {
                    authenticate("auth-oauth-dummy", optional = true) {
                        get {
                            call.respond("GET /oauth")
                        }
                        post {
                            val contentType = call.request.contentType()
                            try {
                                val payload = call.receiveText()
                                call.respond("POST /oauth - $contentType - $payload")
                            } catch (t: Throwable) {
                                call.respond(HttpStatusCode.InternalServerError, "POST /oauth - $contentType - ${t.message}")
                            }
                        }
                    }
                }
            }
        }

        // Works
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("GET /", bodyAsText())
        }

        // Works
        client.get("/oauth").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("GET /oauth", bodyAsText())
        }

        // Works
        client.post("/") {
            setBody(TextContent("foo", ContentType.Text.Plain))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("POST / - text/plain - foo", bodyAsText())
        }

        // Works
        client.post("/oauth") {
            setBody(TextContent("foo", ContentType.Text.Plain))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("POST /oauth - text/plain - foo", bodyAsText())
        }

        // Works
        client.post("/") {
            setBody(TextContent(listOf("foo" to "bar").formUrlEncode(), ContentType.Application.FormUrlEncoded))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("POST / - application/x-www-form-urlencoded - foo=bar", bodyAsText())
        }

        // Fails
        // status is `500`
        // bodyAsText() is `POST /oauth - application/x-www-form-urlencoded - Request body has already been consumed (received).`
        client.post("/oauth") {
            setBody(TextContent(listOf("foo" to "bar").formUrlEncode(), ContentType.Application.FormUrlEncoded))
        }.apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("POST /oauth - application/x-www-form-urlencoded - foo=bar", bodyAsText())
        }
    }
}
