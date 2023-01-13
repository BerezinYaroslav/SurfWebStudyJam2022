package ru.surf.core.entity

import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*

@Table(name = "teams_feedbacks")
@Entity
class TeamFeedback(

    @Column(name = "comment")
    var comment: String = "",

    @Column(name = "score")
    var score: Int = 0,

    @Column(name = "feedback_date")
    var date: LocalDate = LocalDate.now(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    val mentor: SurfEmployee = SurfEmployee(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    val team: Team = Team(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as TeamFeedback

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , comment = $comment , score = $score , date = $date )"
    }

}