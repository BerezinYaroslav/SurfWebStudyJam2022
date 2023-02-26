package ru.surf.defenceofproject.model

import ru.surf.core.entity.Candidate

class Group(
    val id: Int,
    val candidatesList: List<Candidate>
)