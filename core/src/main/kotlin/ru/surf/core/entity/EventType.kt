package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "event_types")
@Entity
class EventType(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "type")
    var description: String = "",

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "eventType")
    var events: MutableList<Event> = mutableListOf(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description )"
    }

}