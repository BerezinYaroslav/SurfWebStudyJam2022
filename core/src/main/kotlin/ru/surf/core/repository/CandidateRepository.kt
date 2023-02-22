package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.surf.core.entity.Candidate
import java.util.*

@Repository
interface CandidateRepository : JpaRepository<Candidate, UUID> {

    @Query(
        """SELECT * FROM candidates c WHERE c.event_id =:event_id """, nativeQuery = true
    )
    fun getCandidatesByEventId(@Param("event_id") eventID: UUID): List<Candidate>

}