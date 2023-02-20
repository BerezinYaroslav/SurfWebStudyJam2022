package ru.surf.report.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.StatesEvents
import ru.surf.report.model.EventStates
import java.util.UUID

interface StatesEventRepository : JpaRepository<StatesEvents, UUID> {
    @Query("select new ru.surf.report.model.EventStates(se.date, st.type) " +
            "from StatesEvents se join se.stateType st where se.event.id = :eventId")
    fun getStatesByEventId(@Param("eventId")eventId: UUID) : List<EventStates>
}