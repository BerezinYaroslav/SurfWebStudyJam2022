package ru.surf.externalfiles.entity

import ru.surf.core.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "question_types")
@Entity
class QuestionType(

        @Id
        @Column(name = "id")
        override val id: UUID = UUID.randomUUID(),

        @Column(name = "type")
        var type: String = "",

        ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , type = $type )"
    }

}
