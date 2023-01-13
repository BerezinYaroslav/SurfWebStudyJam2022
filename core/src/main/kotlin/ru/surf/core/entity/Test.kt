package ru.surf.core.entity

import org.hibernate.Hibernate
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Table(name = "tests")
@Entity
class Test(

    @Column(name = "link")
    val link: String = "",

    @Column(name = "score")
    val score: Int = 0,

    @Column(name = "start_date")
    val startDate: LocalDateTime = LocalDateTime.now(),

    @Column(name = "end_date")
    val endDate: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    val candidate: Candidate = Candidate(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "id")
    val event: Event = Event(),

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinTable(name = "tests_questions",
        joinColumns = [JoinColumn(name = "test_id")],
        inverseJoinColumns = [JoinColumn(name = "question_id")])
    val questions: List<Question> = mutableListOf(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Test

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , link = $link , score = $score , startDate = $startDate , endDate = $endDate )"
    }

}