package ru.surf.report.config

import com.itextpdf.html2pdf.ConverterProperties
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EntityScan("ru.surf.core.entity")
class AppConfig {
    @Bean
    fun converterProperties() : ConverterProperties {
        val converterProperties = ConverterProperties()
        converterProperties.baseUri = "http://localhost:8082"
        return converterProperties
    }
}