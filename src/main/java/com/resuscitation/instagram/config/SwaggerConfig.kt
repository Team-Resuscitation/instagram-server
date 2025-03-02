package com.resuscitation.instagram.config

import com.resuscitation.instagram.config.properties.GitProperties
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig(
    private val gitProperties: GitProperties,
) {
    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().components(
            Components().apply {
                securitySchemes(
                    mapOf(
                        BEARER_AUTH to securityScheme(),
                    ),
                )
            },
        ).info(info())
    }

    /**
     * Bearer Auth Security Scheme
     */
    private fun securityScheme(): SecurityScheme {
        return SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .description(
                """<h3>Instagram Clone 서비스를 이용하기 위해서는 로그인이 필요합니다.</h3>
                <p>엑세스 토큰은 아래 경로를 통해 발급받을 수 있습니다.</p>
                <h4>로그인 방법</h4>
                <ol>
                   <li>`api/v1/auth/login` 경로를 통해 로그인을 진행합니다.</li>
                   <li>`api/v1/auth/signup` 경로를 통해 회원가입을 진행합니다.</li>
                </ol>
                <h4>Oauth 로그인 법</h4>
                <ol>
                   <li><a target="_blank" href="/oauth2/authorization/google">구글 로그인</a></li>
                   <li><a target="_blank" href="/oauth2/authorization/github">깃허브 로그인</a></li>
                </ol>
                <h4>토큰 사용 방법</h4>
                <p>위 링크를 통해 발급받은 토큰을 Bearer Token에 입력하여 사용하세요.</p>
                """.trimIndent(),
            )
            .scheme("bearer")
            .bearerFormat("JWT")
    }

    /**
     * OpenAPI Info
     */
    private fun info(): Info {
        return Info().apply {
            title = "instagram clone server API"
            description =
                """<h3>instagram clone server API Documentation</h3>
                |<p>You can See Full Source Code at <a href="${gitProperties.remote.origin.url}">GitHub Repository</a></p>
                |<p>API Document is generated by Swagger</p>
                |<h4>Last Commit Information</h4>
                |<p><strong>Message:</strong> ${gitProperties.commit.message.full}</p>
                |<p><strong>Commit ID:</strong> ${gitProperties.commit.id}</p>
                |<p><strong>Commit Time:</strong> ${gitProperties.commit.time}</p>
                |<p><strong>Committed by:</strong> ${gitProperties.commit.user.name} (${gitProperties.commit.user.email})</p>
                """.trimMargin()
            termsOfService = ""
            contact =
                Contact().apply {
                    name = "Yooun Chaemin"
                    email = "cinnamein@gmail.com"
                }
            license =
                License().apply {
                    name = "MIT License"
                    url = "https://opensource.org/licenses/MIT"
                }
            version = gitProperties.build.version
        }
    }

    companion object {
        const val BEARER_AUTH: String = "JSON Web Token (JWT)"
        const val USER_API: String = "User API"
        const val USERS_API: String = "Users API"
        const val AUTH_API: String = "Auth API"
        const val POST_API: String = "Post API"
    }
}
