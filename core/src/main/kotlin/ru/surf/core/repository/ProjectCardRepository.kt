package ru.surf.core.repository

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import ru.surf.core.entity.ProjectCard
import java.util.*

interface ProjectCardRepository : JpaRepository<ProjectCard, UUID> {

    /*  @Lock(LockModeType.OPTIMISTIC)
      @Transactional
      @Query("SELECT c FROM ProjectCard c where c.id=:id")
      fun findProjectCardByIdAndLock(id: UUID): ProjectCard?*/

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findWithLockingById(id: UUID): ProjectCard?

}