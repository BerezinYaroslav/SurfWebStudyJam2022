package ru.surf.report.config

import com.itextpdf.html2pdf.ConverterProperties
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
@EntityScan("ru.surf.core.entity")
class ReportModuleConfig(
    @Value("\${services.testing.url}")
    private val testingServiceUrl: String
) {
    @Bean
    fun converterProperties(): ConverterProperties {
        val converterProperties = ConverterProperties()
        converterProperties.baseUri = "http://localhost:8082"
        return converterProperties
    }

    @Bean
    fun restTemplate(): WebClient {
        return WebClient.builder()
            .baseUrl(testingServiceUrl)
            .build()
    }
}