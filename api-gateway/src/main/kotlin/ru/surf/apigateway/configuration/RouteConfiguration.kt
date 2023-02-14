package ru.surf.apigateway.configuration

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfiguration(private val applicationPortsConfiguration: ApplicationPortsConfiguration) {

    object ApplicationNames {
        const val serviceCore: String = "service-core"
        const val externalFiles: String = "service-external-files"
    }

    @Bean
    fun configureRoutes(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
            .route(ApplicationNames.serviceCore) { r ->
                r.path("/core/**")
                    .uri(applicationPortsConfiguration.serviceCorePort)
            }
            .route(ApplicationNames.externalFiles) { r ->
                r.path("/external-files/**").and()
                    .uri(applicationPortsConfiguration.externalFilesPort)
            }
            .build()
    }

}