package ru.surf.report.controller

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.core.kafkaEvents.EndingEvent
import ru.surf.report.service.ReportService
import java.io.ByteArrayOutputStream


@Component
@KafkaListener(topics = ["core-topics"])
class ReportListener(
    private val templateEngine: SpringTemplateEngine,
    private val reportService: ReportService,
    private val converterProperties: ConverterProperties,
) {

    @KafkaHandler
    @RetryableTopic
    fun onEndingEvent(@Payload value: EndingEvent) {
        val context = Context()

        context.setVariable("report", reportService.getReport(value.eventId))
        val html = templateEngine.process("report", context)

        val target = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(html, target, converterProperties)

        reportService.saveReport(target.toByteArray(), value.eventId)
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit
}