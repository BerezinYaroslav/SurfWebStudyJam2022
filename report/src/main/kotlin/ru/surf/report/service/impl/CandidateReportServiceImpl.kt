package ru.surf.report.service.impl

import org.springframework.stereotype.Service
import ru.surf.report.model.CandidateExcelDto
import ru.surf.report.model.CandidatesReport
import ru.surf.report.repository.CandidateRepository
import ru.surf.report.repository.EventRepository
import ru.surf.report.repository.TraineeRepository
import ru.surf.report.service.CandidateReportService
import java.util.*

@Service
class CandidateReportServiceImpl(
    private val candidateRepository: CandidateRepository,
    private val traineeRepository: TraineeRepository,
    private val eventRepository: EventRepository
) : CandidateReportService {
    override fun getReport(eventId: UUID): CandidatesReport {
        return CandidatesReport(
            eventId = eventId,
            candidates = getCandidates(eventId)
        )
    }

    private fun getTags(eventId: UUID): String {
        val tagDescriptions = eventRepository.getEventTags(eventId).map { it.description }
        return tagDescriptions.joinToString(" ")
    }

    private fun getCandidates(eventId: UUID): List<CandidateExcelDto> {
        val idList = traineeRepository.getAllId(eventId)
        val tags = getTags(eventId)
        val candidates = if (idList.isEmpty()) {
            candidateRepository.getApprovedFailedCandidates(eventId)
        } else {
            candidateRepository.getApprovedFailedCandidatesByIdList(idList, eventId)
        }
        return candidates.map { it -> CandidateExcelDto(it.firstName, it.lastName, it.email, tags) }
    }

}