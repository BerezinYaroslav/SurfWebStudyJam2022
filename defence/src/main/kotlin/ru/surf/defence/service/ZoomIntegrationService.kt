package ru.surf.defence.service

import org.springframework.http.ResponseEntity
import ru.surf.defence.dto.ZoomCreateMeetingRequestDto
import ru.surf.defence.dto.zoom.ZoomAdminUserResponse
import ru.surf.defence.dto.zoom.ZoomCreateMeetingResponseDto
import ru.surf.defence.dto.zoom.ZoomAccessTokenResponseDto

interface ZoomIntegrationService {

    fun getZoomAdminUserInformation(userId: String): ZoomAdminUserResponse

    fun refreshAccessToken(): ZoomAccessTokenResponseDto

    fun createZoomMeeting(zoomCreateMeetingRequestDto: ZoomCreateMeetingRequestDto): ZoomCreateMeetingResponseDto

    fun deleteZoomMeeting(zoomMeetingId: Long): ResponseEntity<Void>

}