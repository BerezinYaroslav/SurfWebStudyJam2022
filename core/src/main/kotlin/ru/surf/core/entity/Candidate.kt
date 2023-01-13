package ru.surf.core.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name = "candidates")
@Entity
class Candidate(

    @Column(name = "name")
    var name: String = "",

    @Column(name = "email")
    var email: String = "",

    @Column(name = "is_new")
    var isNew: Boolean = false,

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_from_id", referencedColumnName = "id")
    val hr: SurfEmployee = SurfEmployee(),

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinTable(name = "candidates_events",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "candidate_id")])
    val events: List<Event> = mutableListOf(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "candidate")
    val trainees: List<Trainee> = emptyList(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Candidate

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , name = $name , email = $email , isNew = $isNew )"
    }

}