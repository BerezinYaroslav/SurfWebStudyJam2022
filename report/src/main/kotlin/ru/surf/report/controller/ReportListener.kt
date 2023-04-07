package ru.surf.report.controller

import org.springframework.kafka.annotation.KafkaHandler
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.surf.core.kafkaEvents.EndingEvent
import ru.surf.report.service.EventReportService
import ru.surf.report.service.ReportWrapper


@Component
@KafkaListener(topics = ["core-topics"])
class ReportListener(
    private val eventReportService: EventReportService,
    private val reportWrapper: ReportWrapper
) {

    @KafkaHandler
    @RetryableTopic
    fun onEndingEvent(@Payload value: EndingEvent) {
        val report = reportWrapper.wrap(
            eventReportService.getReport(value.eventId)
        )

        eventReportService.saveReport(report, value.eventId)
    }

    @KafkaHandler(isDefault = true)
    fun default() = Unit
}