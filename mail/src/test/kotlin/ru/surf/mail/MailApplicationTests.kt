package ru.surf.mail

import com.icegreen.greenmail.configuration.GreenMailConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.extension.RegisterExtension
import com.icegreen.greenmail.junit5.GreenMailExtension
import org.junit.jupiter.api.Assertions.assertEquals
import ru.surf.mail.model.GeneralNotificationDto
import com.icegreen.greenmail.util.ServerSetup
import ru.surf.mail.model.TopicTemplate
import org.junit.jupiter.api.BeforeAll
import kotlin.concurrent.schedule
import org.junit.jupiter.api.Test
import java.util.*


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MailApplicationTests {
    companion object {
        @RegisterExtension
        var greenMail: GreenMailExtension = GreenMailExtension(ServerSetup.SMTP.withPort(3025)).withConfiguration(
            GreenMailConfiguration.aConfig().withUser("user", "admin")
        )

        @JvmStatic
        @DynamicPropertySource
        fun kafkaProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.kafka.consumer.bootstrap-servers") { KafkaBase.kafkaContainer.bootstrapServers }
            registry.add("spring.kafka.producer.bootstrap-servers") { KafkaBase.kafkaContainer.bootstrapServers }
        }

        @JvmStatic
        @BeforeAll
        fun setup() {
            KafkaBase.createTopic(TopicTemplate.SIMPLE_NOTIFICATION_TOPIC)
        }
    }

    @Test
    fun `should send simple email notification`() {
        val email = GeneralNotificationDto("test_mail@surf.ru", "Simple Notification", mapOf("name" to "Test"))
        KafkaBase.writeToTopic(email, TopicTemplate.SIMPLE_NOTIFICATION_TOPIC)

        Timer().schedule(2000) {
            assertEquals(email.subject, greenMail.receivedMessages.first().subject)
        }
    }
}
