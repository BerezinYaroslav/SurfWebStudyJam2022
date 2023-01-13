package ru.surf.core.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name = "trainees")
@Entity
class Trainee(

    @Column(name = "score")
    var score: Int = 0,

    @Column(name = "is_active")
    var isActive: Boolean = false,

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    val candidate: Candidate = Candidate(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event = Event(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    val team: Team = Team(),

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    val account: Account = Account(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "traineeReceiver")
    val trainees: List<TraineeFeedback> = emptyList(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Trainee

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , score = $score , isActive = $isActive )"
    }


}