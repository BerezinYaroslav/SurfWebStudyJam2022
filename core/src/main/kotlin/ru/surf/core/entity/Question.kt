package ru.surf.core.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name = "questions")
@Entity
class Question(

    @Column(name = "question")
    var description: String = "",

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "question_type_id")
    val type: QuestionType = QuestionType(),

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "questions")
    val tests: List<Answer> = mutableListOf(),

    @ManyToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinTable(name = "questions_answers",
        joinColumns = [JoinColumn(name = "answer_id")],
        inverseJoinColumns = [JoinColumn(name = "question_id")])
    val answers: List<Answer> = mutableListOf(),

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "right_answer_id", referencedColumnName = "id")
    val rightAnswer: Answer = Answer(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Question

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description , rightAnswer = $rightAnswer )"
    }

}