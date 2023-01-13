package ru.surf.core.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name = "accounts")
@Entity
class Account(

    @Column(name = "email")
    var email: String = "",

    @Column(name = "password")
    var password: String = "",

    @ManyToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    val role: Role = Role(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Account

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , email = $email , password = $password )"
    }

}