package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.surf.core.entity.Trainee
import java.util.UUID

interface TraineeRepository: JpaRepository<Trainee, UUID> {
    fun countByEventId(eventId: UUID) : Int
}