package at.bernhardfleck.TimeOverlappingTool.configuration

import at.bernhardfleck.TimeOverlappingTool.domain.Survey
import at.bernhardfleck.TimeOverlappingTool.service.SurveyService
import org.slf4j.LoggerFactory.getLogger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.config.EnableIntegration
import org.springframework.integration.dsl.*
import org.springframework.integration.jpa.dsl.Jpa
import javax.persistence.EntityManagerFactory

@Profile("!test")
@Configuration
@EnableIntegration
class SpringIntegrationConfig(
    @Autowired val entityManagerFactory: EntityManagerFactory,
    @Autowired val surveyService: SurveyService,
) {
    val logger = getLogger("springIntegrationLogger")

    @Value("\${spring.integration.pollingRateInMilliSec}")
    private val pollingRateInMilliSec: Long = 3000

    @Bean
    fun inboundAdapterFlowFromSurveys(): IntegrationFlow {
        val finishedSurveyChannel = "finishedSurveyChannel"
        val finishedSurveys = "from Survey where endDate < CURRENT_DATE"

        return IntegrationFlows.from(
            Jpa.inboundAdapter(entityManagerFactory)
                .entityClass(Survey::class.java)
                .jpaQuery(finishedSurveys)
        ) { e: SourcePollingChannelAdapterSpec ->
            e.poller { p: PollerFactory ->
                p.fixedDelay(pollingRateInMilliSec)
            }
        }
            .channel { c: Channels -> c.direct(finishedSurveyChannel) }
            .get()
    }

    @ServiceActivator(inputChannel = "finishedSurveyChannel")
    fun actionOnObserved(surveys: List<Survey>) {
        delete(surveys)
        log(surveys)
    }

    private fun delete(surveys: List<Survey>) {
        surveys.forEach { surveyService.delete(it) }
    }

    private fun log(surveys: List<Survey>) {
        logger.info("Deleted surveys:")
        surveys.forEach { logger.info(it.toString()) }
    }
}