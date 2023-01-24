package ru.surf.core.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.surf.core.entity.Trainee
import java.util.UUID

interface TraineeRepository : JpaRepository<Trainee, UUID> {

    @Query("SELECT * FROM trainees t WHERE t.id IN :ids ", nativeQuery = true)
    fun findTraineesByIdIn(@Param("ids") ids: List<UUID>): MutableList<Trainee>

}