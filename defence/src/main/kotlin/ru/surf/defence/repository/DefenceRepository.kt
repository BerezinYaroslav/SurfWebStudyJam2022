package ru.surf.defence.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.defence.entity.Defence
import java.util.*

interface DefenceRepository : JpaRepository<Defence, UUID> {

    fun findDefenceByZoomMeetingId(zoomMeetingId: Long): Defence?

}