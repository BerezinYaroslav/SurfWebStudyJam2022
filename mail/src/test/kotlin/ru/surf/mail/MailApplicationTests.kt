package ru.surf.mail

import com.icegreen.greenmail.configuration.GreenMailConfiguration
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.extension.RegisterExtension
import com.icegreen.greenmail.junit5.GreenMailExtension
import org.junit.jupiter.api.Assertions.assertEquals
import com.icegreen.greenmail.util.ServerSetup
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import ru.surf.mail.model.EmailType
import ru.surf.mail.model.dto.GeneralNotificationDto

//@ContextConfiguration(classes = [KafkaTestConfig::class])
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
            KafkaBase.createTopic("core-topics")
        }
    }

    @Test
    fun `should send simple email notification`() {
        val email = GeneralNotificationDto(
            EmailType.ACCEPT_APPLICATION,
            "test_mail@surf.ru",
            "Accept application Test",
            mapOf("firstName" to "FirstName", "lastName" to "LastName", "eventName" to "EventName")
        )
        KafkaBase.writeToTopic(email, "core-topics")

        Thread.sleep(2000)
        assertEquals(email.subject, greenMail.receivedMessages.first().subject)
    }
}
