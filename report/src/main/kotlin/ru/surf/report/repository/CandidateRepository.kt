package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.Candidate
import java.util.UUID

interface CandidateRepository : JpaRepository<Candidate, UUID> {
    @Query(
        value = "select count(*) from candidates_events where event_id = :eventId",
        nativeQuery = true)
    fun countByEventId(@Param("eventId") eventId: UUID): Int
}