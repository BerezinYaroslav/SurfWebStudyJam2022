package ru.surf.defence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import ru.surf.core.entity.base.UUIDBasedEntity
import java.time.LocalDateTime
import java.util.*


@Entity
@Table(name = "defences")
class Defence(

    @Id
    @Column(name = "id")
    override var id: UUID = UUID.randomUUID(),

    @Column(name = "title")
    var title: String = "",

    @Column(name = "description")
    var description: String = "",

    @Column(name = "zoom_link")
    var zoomLink: String? = null,

    @Column(name = "date_time")
    val date: LocalDateTime = LocalDateTime.now(),

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "zoom_meeting_id")
    val zoomMeetingId: Long = 0

) : UUIDBasedEntity(id) {

}