package ru.surf.defenceofproject.model

import ru.surf.core.entity.SurfEmployee

class Jury(
    val id: Int,
    val mentorsList: List<SurfEmployee>
)