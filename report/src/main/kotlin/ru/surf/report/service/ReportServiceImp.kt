package ru.surf.report.service

import org.springframework.stereotype.Service
import ru.surf.report.model.Report
import ru.surf.report.repository.*
import java.util.*

@Service
class ReportServiceImp(
    private val testRepository: TestRepository,
    private val statesEventRepository: StatesEventRepository,
    private val teamRepository: TeamRepository,
    private val eventRepository: EventRepository,
    private val candidateRepository: CandidateRepository,
    private val traineeRepository: TraineeRepository
) : ReportService {

    override fun getPdfReport(eventId: UUID): Report {
        val report = Report()

        report.eventDescription = eventRepository.getDescriptionById(eventId)
        report.eventStates = statesEventRepository.getStatesByEventId(eventId)
        report.peopleAmountByStates = getPeopleAmountByState(eventId)
        report.testResults = getTestResultsByGroup(eventId)
        report.teamResults = getTeamsWithAvgScore(eventId)

        return report
    }

    private fun getTestResultsByGroup(eventId: UUID): MutableMap<Int, Int> {
        val results = testRepository.getTestScoresByEventId(eventId)
        val resultsByGroup = mutableMapOf(
            0 to 0,
            1 to 0,
            2 to 0,
            3 to 0
        )
        results.forEach {
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
        peopleAmountByState[1] = testRepository.countByScoreIsNotNull()
        peopleAmountByState[2] = traineeRepository.countByEventId(eventId)

        return peopleAmountByState
    }
}