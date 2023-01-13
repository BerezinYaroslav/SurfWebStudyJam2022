package ru.surf.core.entity

import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*

@Table(name = "trainees_feedbacks")
@Entity
class TraineeFeedback(

    @Column(name = "score")
    var score: Int = 0,

    @Column(name = "comment")
    var comment: String = "",

    @Column(name = "date")
    var date: LocalDate = LocalDate.now(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "surf_employee_id")
    val ownerFeedback: SurfEmployee = SurfEmployee(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "trainee_id")
    val traineeReceiver: Trainee = Trainee()

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as TraineeFeedback

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , score = $score , comment = $comment , date = $date )"
    }

}