package ru.surf.core.entity

import org.hibernate.Hibernate
import javax.persistence.*


@Table(name = "answers")
@Entity
class Answer(

    @Column(name = "answer")
    var text: String = "",

    @ManyToMany(mappedBy = "answers", fetch = FetchType.LAZY)
    val questions: List<Question> = mutableListOf(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Answer

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , text = $text )"
    }

}