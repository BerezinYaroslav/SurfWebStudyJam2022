package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "state_types")
@Entity
class StateType(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "type")
    var type: String = "",

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "stateType")
    var statesEvents: MutableList<StatesEvents> = mutableListOf(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , type = $type )"
    }

}