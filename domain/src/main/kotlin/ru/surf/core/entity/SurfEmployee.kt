package ru.surf.core.entity

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import ru.surf.externalfiles.entity.S3File
import javax.persistence.*

@Table(name = "surf_employees")
@Entity
class SurfEmployee(

    @Column(name = "name", nullable = false)
    var name: String = "",

    @OneToMany(cascade = [CascadeType.REFRESH], fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JoinColumn(name = "s3file_id")
    var s3files: Set<S3File> = setOf(),

    ) : Account() {

    override fun toString(): String {
        return "SurfEmployee(name='$name') ${super.toString()}"
    }

}