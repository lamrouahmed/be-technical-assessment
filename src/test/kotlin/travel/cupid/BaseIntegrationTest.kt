package travel.cupid

import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.github.tomakehurst.wiremock.junit5.WireMockExtension
import com.redis.testcontainers.RedisContainer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
abstract class BaseIntegrationTest {
    companion object {

        private val postgres = PostgreSQLContainer("postgres:16-alpine")

        private val redis = RedisContainer(DockerImageName.parse("redis:8-alpine"))

        @BeforeAll
        @JvmStatic
        fun startContainers() {
            postgres.start()
            redis.start()
        }

        @AfterAll
        @JvmStatic
        fun stopContainers() {
            postgres.stop()
            redis.stop()
        }

        @DynamicPropertySource
        @JvmStatic
        fun registerDBContainer(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgres::getJdbcUrl)
            registry.add("spring.datasource.username", postgres::getUsername)
            registry.add("spring.datasource.password", postgres::getPassword)

            registry.add("spring.data.redis.host", redis::getHost)
            registry.add("spring.data.redis.port") { redis.getMappedPort(6379).toString() }
            registry.add("spring.data.redis.password") { "" }

            registry.add("connectors.cupid.api.url") { "http://localhost:${wiremock.runtimeInfo.httpPort}/v3.0" }
        }

        @JvmField
        @RegisterExtension
        val wiremock: WireMockExtension = WireMockExtension.newInstance()
            .options(
                WireMockConfiguration.options().dynamicPort(),
            ).build()
    }
}
