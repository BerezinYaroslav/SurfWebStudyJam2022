package ru.surf.report.model

data class Report(
    var eventDescription: String = "",
    var eventStates: List<EventStates> = mutableListOf(),
    var peopleAmountByStates: Map<Int, Int> = mutableMapOf(),
    var testResults: Map<Int, Int> = mutableMapOf(),
    var teamResults: Map<String, Double> = mutableMapOf()
)