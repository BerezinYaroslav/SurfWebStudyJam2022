package ru.surf.report.service

import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlow
import ru.surf.report.model.Report
import ru.surf.report.repository.*
import java.util.*

@Service
class ReportServiceImp(
    private val teamRepository: TeamRepository,
    private val eventRepository: EventRepository,
    private val candidateRepository: CandidateRepository,
    private val traineeRepository: TraineeRepository,
    private val webClient: WebClient,
) : ReportService {
    private lateinit var testResults : List<Int?>

    override fun getPdfReport(eventId: UUID): Report {
        val report = Report()

        getTestResult(eventId)

        report.eventDescription = eventRepository.getDescriptionById(eventId)
        report.eventStates = eventRepository.getStatesById(eventId)
        report.peopleAmountByStates = getPeopleAmountByState(eventId)
        report.testResults = getTestResultsByGroup()
        report.teamResults = getTeamsWithAvgScore(eventId)

        return report
    }

    private fun getTestResult(eventId: UUID) {
        testResults = runBlocking {
            webClient.get()
                .uri("test/{eventId}}/scores", eventId)
                .retrieve()
                .bodyToFlow<Int>()
                .toList()
        }
    }
    private fun getTestResultsByGroup(): MutableMap<Int, Int> {
        val resultsByGroup = mutableMapOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 0
        )
        testResults.forEach {
            when(it ?: 0) {
                in 0..25 -> resultsByGroup[0] = resultsByGroup[0]!! + 1
                in 26..50 -> resultsByGroup[1] = resultsByGroup[1]!! + 1
                in 51..75 -> resultsByGroup[2] = resultsByGroup[2]!! + 1
                in 76..100 -> resultsByGroup[3] = resultsByGroup[3]!! + 1
            }
        }
        return resultsByGroup
    }

    private fun getTeamsWithAvgScore(eventId: UUID): MutableMap<String, Double> {
        val teamsWithScore = mutableMapOf<String, Double>()
        teamRepository.getTeamsMentor(eventId).forEach {
            teamsWithScore[it.mentorName] = teamRepository.getTeamScoreById(it.id).average()
        }
        return teamsWithScore
    }

    private fun getPeopleAmountByState(eventId: UUID): MutableMap<Int, Int> {
        val peopleAmountByState = mutableMapOf<Int, Int>()

        peopleAmountByState[0] = candidateRepository.countByEventId(eventId)
        peopleAmountByState[1] = testResults.count { it != null }
        peopleAmountByState[2] = traineeRepository.countByEventId(eventId)

        return peopleAmountByState
    }
}