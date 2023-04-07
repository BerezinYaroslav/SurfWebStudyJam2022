package ru.surf.report.service

import ru.surf.report.model.Report
import java.util.UUID

interface EventReportService {
    fun getReport(eventId: UUID): Report
    fun saveReport(reportByteArray: ByteArray, eventId: UUID)
}