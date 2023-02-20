package ru.surf.report.controller

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.thymeleaf.context.WebContext
import org.thymeleaf.spring5.SpringTemplateEngine
import ru.surf.report.service.ReportService
import java.io.ByteArrayOutputStream
import java.util.*
import javax.servlet.ServletContext
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
@RequestMapping(value = ["/report"])
class ReportController(
    private val templateEngine: SpringTemplateEngine,
    private val servletContext: ServletContext,
    private val request: HttpServletRequest,
    private val response: HttpServletResponse,
    private val reportService: ReportService,
    private val converterProperties: ConverterProperties
) {

    @GetMapping("/pdf/{event_id}")
    fun getPdf(@PathVariable(name = "event_id") eventId: UUID): ResponseEntity<ByteArray> {
        val context = WebContext(request, response, servletContext)

        context.setVariable("report", reportService.getPdfReport(eventId))
        val html = templateEngine.process("report", context)

        val target = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(html, target, converterProperties)

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(target.toByteArray())
    }
}