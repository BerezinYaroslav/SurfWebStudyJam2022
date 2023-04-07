package ru.surf.report.service.impl

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import ru.surf.core.entity.Candidate
import ru.surf.report.repository.CandidateRepository
import ru.surf.report.repository.EventRepository
import ru.surf.report.repository.TraineeRepository
import ru.surf.report.service.CandidateReportService
import java.io.ByteArrayOutputStream
import java.util.UUID

@Service
class CandidateReportServiceImpl(
    private val candidateRepository: CandidateRepository,
    private val traineeRepository: TraineeRepository,
    private val eventRepository: EventRepository
) : CandidateReportService {
    override fun getReport(eventId: UUID): ByteArray {
        val candidates = getCandidates(eventId)

        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("candidates")
        val header = sheet.createRow(0)

        header.createCell(0).setCellValue("First Name")
        header.createCell(1).setCellValue("Last Name")
        header.createCell(2).setCellValue("Email")
        header.createCell(3).setCellValue("Tags")

        candidates.forEachIndexed { index: Int, candidate: Candidate ->
            val row = sheet.createRow(index + 1)

            row.createCell(0).setCellValue(candidate.firstName)
            row.createCell(1).setCellValue(candidate.lastName)
            row.createCell(2).setCellValue(candidate.email)
            row.createCell(3).setCellValue(getTags(eventId))
        }

        val target = ByteArrayOutputStream()
        workbook.write(target)

        return target.toByteArray()
    }

    private fun getTags(eventId: UUID): String {
        val tagDescriptions = eventRepository.getEventTags(eventId).map { it.description }
        return tagDescriptions.joinToString(" ")
    }

    private fun getCandidates(eventId: UUID): List<Candidate> {
        val idList = traineeRepository.getAllId(eventId)
        return if (idList.isEmpty()) {
            candidateRepository.getApprovedFailedCandidates(eventId)
        } else {
            candidateRepository.getApprovedFailedCandidatesByIdList(idList, eventId)
        }
    }

}