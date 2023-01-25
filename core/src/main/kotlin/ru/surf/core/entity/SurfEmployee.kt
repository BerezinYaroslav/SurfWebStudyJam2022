package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*

@Table(name = "surf_employees")
@Entity
class SurfEmployee(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "name")
    var name: String = "",

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    var account: Account = Account(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "eventInitiator")
    var events: MutableList<Event> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "ownerFeedback")
    var feedbacksForTrainee: MutableList<TraineeFeedback> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "mentor")
    var feedbacksForTeam: MutableList<TeamFeedback> = mutableListOf(),

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , account = $account )"
    }

}