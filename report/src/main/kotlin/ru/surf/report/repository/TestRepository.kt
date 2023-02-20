package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.Test
import java.util.UUID

interface TestRepository: JpaRepository<Test, UUID> {
    @Query(value = "select t.score from tests t where t.event_id = :eventId", nativeQuery = true)
    fun getTestScoresByEventId(@Param("eventId") eventId: UUID) : List<Int?>

    fun countByScoreIsNotNull(): Int
}