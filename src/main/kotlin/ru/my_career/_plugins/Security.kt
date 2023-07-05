package ru.my_career._plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.engine.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import org.koin.ktor.ext.inject
import ru.my_career._common.types.ResponseEntity

fun Application.configureSecurity() {
    authentication {
        jwt("jwt") {
            val jwtAudience = this@configureSecurity.environment.config.property("jwt.audience").getString()
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            val secret = this@configureSecurity.environment.config.property("jwt.secret").getString()
            val issuer = this@configureSecurity.environment.config.property("jwt.issuer").getString()

            verifier(
                JWT
                    .require(Algorithm.HMAC256(secret))
                    .withAudience(jwtAudience)
                    .withIssuer(issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
            challenge { defaultScheme, realm ->
                val res = ResponseEntity<String>(
                    HttpStatusCode.Unauthorized,
                    errorMessage = "Token is not valid or has expired"
                )
                call.respond(res.statusCode, res)
            }
        }
    }
}
