package ru.surf.externalfiles.entity


import org.hibernate.envers.Audited
import ru.surf.externalfiles.entity.base.UUIDBasedEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Audited
@Table(name = "s3files")
@Entity
class S3File(

    @Id
    @Column(name = "id")
    override var id: UUID = UUID.randomUUID(),

    @Column(name = "content_type")
    var contentType: String = "",

    @Column(name = "s3_key")
    var s3Key: String = "",

    @Column(name = "size_in_bytes")
    var sizeInBytes: Long = 0,

    @Column(name = "s3_filename")
    var s3Filename: String = "",

    @Column(name = "checksum")
    var checksum: String = "",

    ) : UUIDBasedEntity(id) {

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , contentType = $contentType , s3Key = $s3Key , sizeInBytes = $sizeInBytes , s3FilePath = $s3Filename )"
    }

}