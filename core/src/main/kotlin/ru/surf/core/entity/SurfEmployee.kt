package ru.surf.core.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name = "surf_employees")
@Entity
class SurfEmployee(

    @Column(name = "name")
    var name: String = "",

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    val account: Account = Account(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "eventInitiator")
    val events: List<Event> = emptyList(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "ownerFeedback")
    val feedbacksForTrainee: List<TraineeFeedback> = emptyList(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "mentor")
    val feedbacksForTeam: List<TeamFeedback> = emptyList(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SurfEmployee

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , account = $account )"
    }

}