package travel.cupid

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing
@EnableFeignClients
@EnableScheduling
@EnableTransactionManagement
@EnableAsync
class CupidTechnicalAssessmentApplication

fun main(args: Array<String>) {
    runApplication<CupidTechnicalAssessmentApplication>(*args)
}
