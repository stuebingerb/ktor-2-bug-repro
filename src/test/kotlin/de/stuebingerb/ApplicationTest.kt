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
import io.ktor.server.plugins.doublereceive.DoubleReceive
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
            install(DoubleReceive)

            routing {
                route("/") {
                    post {
                        val contentType = call.request.contentType()
                        val payload = call.receiveText()
                        call.respond("POST / - $contentType - $payload")
                    }
                }
                route("/oauth") {
                    authenticate("auth-oauth-dummy", optional = true) {
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
