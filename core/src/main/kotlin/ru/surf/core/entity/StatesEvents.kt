package ru.surf.core.entity

import org.hibernate.Hibernate
import java.time.LocalDate
import javax.persistence.*

@Table(name = "states_events")
@Entity
class StatesEvents(

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    val stateType: StateType = StateType(),

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    val event: Event = Event(),

    @Column(name = "date")
    val date: LocalDate = LocalDate.now(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as StatesEvents

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , date = $date )"
    }

}