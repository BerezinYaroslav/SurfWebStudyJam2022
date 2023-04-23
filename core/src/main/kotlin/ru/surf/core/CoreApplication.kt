package ru.surf.core

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.surf.core.configuration.KafkaProducerPropertiesConfiguration
import ru.surf.core.dto.card.PutRequestCardDto
import ru.surf.core.service.impl.ProjectCardServiceImpl
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors

@EnableConfigurationProperties(KafkaProducerPropertiesConfiguration::class)
@SpringBootApplication
class CoreApplication(val cardService: ProjectCardServiceImpl) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val id = UUID.fromString("a7a5a364-49b4-454f-9906-3468964ad132")
        val card = PutRequestCardDto("new note2", 0)
        val card2 = PutRequestCardDto("new note1", 0)
        val executor = Executors.newFixedThreadPool(2)
        executor.submit(Callable {
            try {
                Thread.sleep(2_000)
                cardService.updateProjectCard(id, card)
            } catch (e: Exception) {
                println("Исключение возникло при 1 вызове")
                e.printStackTrace()
            }
        })
        executor.submit(Callable {
            try {
                Thread.sleep(2_000)
                cardService.updateProjectCard(id, card2)
            } catch (e: Exception) {
                println("Исключение возникло при 2 вызове")
                e.printStackTrace()
            }
        })
        executor.shutdown()
    }
}

fun main(args: Array<String>) {
    runApplication<CoreApplication>(*args)
}
