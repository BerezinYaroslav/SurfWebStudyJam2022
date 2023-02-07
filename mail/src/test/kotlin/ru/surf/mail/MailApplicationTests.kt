package ru.surf.mail

import com.fasterxml.jackson.databind.ObjectMapper
import com.icegreen.greenmail.configuration.GreenMailConfiguration
import com.icegreen.greenmail.junit5.GreenMailExtension
import com.icegreen.greenmail.util.ServerSetup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import ru.surf.mail.model.Email

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MailApplicationTests(
    @LocalServerPort
    private val port: Int
) {
    @Autowired
    lateinit var testRestTemplate: TestRestTemplate
    private val mapper = ObjectMapper()

    companion object {
        @RegisterExtension
        var greenMail = GreenMailExtension(ServerSetup.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("user", "admin"))
    }

    @Test
    fun `send mail`() {
        val context: MutableMap<String, Any> = mutableMapOf()
        context["name"] = "Foo"
        val email = mapper.writeValueAsString(Email("test@mail.com", "Test", context))

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val emailRequest: HttpEntity<String> = HttpEntity(email, headers)

        val responseEntity = testRestTemplate.postForEntity("http://localhost:${port}/mail/auth/greeting", emailRequest, Void::class.java)
        assertEquals("Test", greenMail.receivedMessages.first().subject)
    }
}
