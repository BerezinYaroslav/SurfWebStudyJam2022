package ru.surf.externalfiles.controller

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import ru.surf.externalfiles.dto.PostResponseDto
import ru.surf.externalfiles.service.S3FacadeService
import ru.surf.externalfiles.service.S3FileService
import java.util.UUID

//TODO: реализовать привязку файла к пользователю
//Пока непонятно в каком виде придет запрос из сервиса core

@RestController
@RequestMapping("/files")
class S3FileController(private val s3FacadeService: S3FacadeService) {

    @PostMapping(
        "/file",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun uploadFile(@RequestParam(name = "file") multipartFile: MultipartFile): ResponseEntity<PostResponseDto> =
        ResponseEntity.ok(s3FacadeService.saveFile(multipartFile))

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun downloadFile(@PathVariable(name = "id") id: UUID): ResponseEntity<ByteArrayResource> {
        return ResponseEntity.ok(s3FacadeService.getFile(id))
    }

    @DeleteMapping("/{id}", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteFile(@PathVariable(name = "id") id: UUID): ResponseEntity<Unit> =
        ResponseEntity.ok(s3FacadeService.deleteFile(id))

}