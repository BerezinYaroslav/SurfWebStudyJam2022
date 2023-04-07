package ru.surf.report.service

import java.util.UUID

interface CandidateReportService {
    fun getReport(eventId: UUID): ByteArray
}