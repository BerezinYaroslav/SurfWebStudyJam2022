package ru.surf.core.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.*


@Table(name = "answers")
@Entity
class Answer(

    @Id
    @Column(name = "id")
    override val id: UUID = UUID.randomUUID(),

    @Column(name = "answer")
    var text: String = "",

    @ManyToMany(mappedBy = "answers", fetch = FetchType.LAZY)
    var questions: MutableSet<Question> = mutableSetOf()

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , text = $text )"
    }

}