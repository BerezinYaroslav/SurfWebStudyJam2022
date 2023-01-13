package ru.surf.core.entity

import org.hibernate.Hibernate
import javax.persistence.*

@Table(name = "teams")
@Entity
class Team(

    @Column(name = "about")
    var description: String = "",

    @Column(name = "project_git_link")
    val projectGitLink: String = "",

    @Column(name = "project_miro_link")
    val projectMiroLink: String = "",

    @OneToOne(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id", referencedColumnName = "id")
    val mentor: SurfEmployee = SurfEmployee(),

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.LAZY, mappedBy = "team")
    val feedbacks: List<TeamFeedback> = emptyList(),

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Team

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , description = $description , projectGitLink = $projectGitLink , projectMiroLink = $projectMiroLink , mentor = $mentor )"
    }

}