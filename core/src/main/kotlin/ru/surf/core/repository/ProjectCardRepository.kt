package ru.surf.core.repository

import jakarta.persistence.LockModeType
import jakarta.persistence.QueryHint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.transaction.annotation.Transactional
import ru.surf.core.entity.ProjectCard
import java.util.*

interface ProjectCardRepository : JpaRepository<ProjectCard, UUID> {

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_READ)
    fun findWithPessimisticReadLockingById(id: UUID): ProjectCard?

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(QueryHint(name = "jakarta.persistence.lock.timeout", value = "0"))
    fun findWithPessimisticWriteLockingById(id: UUID): ProjectCard?

}
