package ru.surf.report.controller

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import ru.surf.report.service.ReportService
import java.io.ByteArrayOutputStream
import java.util.*


@Controller
@RequestMapping(value = ["/final_report"])
class ReportController(
    private val templateEngine: SpringTemplateEngine,
    private val reportService: ReportService,
    private val converterProperties: ConverterProperties,
) {

    @GetMapping("/pdf/{event_id}")
    fun getPdf(@PathVariable(name = "event_id") eventId: UUID): ResponseEntity<ByteArray> {
        val context = Context()

        context.setVariable("report", reportService.getReport(eventId))
        val html = templateEngine.process("report", context)

        val target = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(html, target, converterProperties)

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(target.toByteArray())
    }
}