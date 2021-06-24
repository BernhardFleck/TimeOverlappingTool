package at.bernhardfleck.TimeOverlappingTool.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
import org.thymeleaf.spring5.ISpringTemplateEngine
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.templateresolver.ITemplateResolver
import java.util.*


@Configuration
class SpringConfig {

    @Bean
    fun templateEngine(templateResolver: ITemplateResolver): ISpringTemplateEngine {
        val engine = SpringTemplateEngine()

        engine.addDialect(Java8TimeDialect())
        engine.setTemplateResolver(templateResolver)
        return engine
    }

    @Bean
    fun setLanguageToEnglish(): LocaleResolver {
        val localeResolver = SessionLocaleResolver()
        localeResolver.setDefaultLocale(Locale.US)
        return localeResolver
    }
}