package ru.surf.defence.service.impl

import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import ru.surf.defence.configuration.ZoomAdminPropertiesConfiguration
import ru.surf.defence.configuration.ZoomPropertiesConfiguration
import ru.surf.defence.dto.ZoomCreateMeetingRequestDto
import ru.surf.defence.dto.zoom.ZoomAccessTokenResponseDto
import ru.surf.defence.dto.zoom.ZoomAdminUserResponse
import ru.surf.defence.dto.zoom.ZoomCreateMeetingResponseDto
import ru.surf.defence.exception.ZoomClientException
import ru.surf.defence.service.ZoomIntegrationService
import java.time.LocalDateTime
import java.util.*

@Service
class ZoomIntegrationServiceImpl(
    private val zoomWebClient: WebClient,
    private val zoomAdminPropertiesConfiguration: ZoomAdminPropertiesConfiguration,
    private val zoomPropertiesConfiguration: ZoomPropertiesConfiguration
) : ZoomIntegrationService {

    companion object Logger {
        val logger = LoggerFactory.getLogger(ZoomIntegrationServiceImpl::class.java)
    }

    object ZoomUrl {

        object OAuth {
            const val oauthUrl = "https://zoom.us/oauth/token"
        }

        object API {
            const val usersUrl = "https://api.zoom.us/v2/users/"
            const val meetingsUrl = "https://api.zoom.us/v2/meetings/"
        }

    }

    override fun getZoomAdminUserInformation(userId: String): ZoomAdminUserResponse {
        return zoomWebClient.get()
            .uri(ZoomUrl.API.usersUrl + zoomAdminPropertiesConfiguration.adminId)
            .header(HttpHeaders.AUTHORIZATION, zoomAdminPropertiesConfiguration.authorizationBearerToken)
            .retrieve()
            .bodyToMono(ZoomAdminUserResponse::class.java)
            .doOnError { error ->
                logger.error("Exception happened:${error.message}")
                throw ZoomClientException(error.message ?: "")
            }
            .block().also {
                logger.info("Zoom Admin Info:$it")
            } ?: throw RuntimeException()
    }

    @Scheduled(fixedRate = 3000000)
    override fun refreshAccessToken(): ZoomAccessTokenResponseDto {
        val multiValueMap = LinkedMultiValueMap<String, String>()
        multiValueMap["grant_type"] = "account_credentials"
        multiValueMap["account_id"] = zoomPropertiesConfiguration.accountId
        val zoomAccessTokenResponseDto = zoomWebClient.post()
            .uri(ZoomUrl.OAuth.oauthUrl)
            .body(BodyInserters.fromFormData(multiValueMap))
            .header(HttpHeaders.AUTHORIZATION, zoomAdminPropertiesConfiguration.authorizationBasicToken)
            .retrieve()
            .bodyToMono(ZoomAccessTokenResponseDto::class.java)
            .doOnError { error ->
                logger.error("Exception happened:${error.message}")
                throw ZoomClientException(error.message ?: "")
            }
            .block() ?: throw RuntimeException()
        zoomAdminPropertiesConfiguration.authorizationBearerToken =
            "Bearer " + zoomAccessTokenResponseDto.accessToken
        logger.info("Zoom Access Token is:$zoomAccessTokenResponseDto")
        logger.info(
            "Current Bearer Token is: ${zoomAdminPropertiesConfiguration.authorizationBearerToken}, expiration time: " +
                    "${LocalDateTime.now().plusMinutes(zoomAccessTokenResponseDto._expiresInMinutes.toLong())}"
        )
        return zoomAccessTokenResponseDto
    }

    override fun createZoomMeeting(zoomCreateMeetingRequestDto: ZoomCreateMeetingRequestDto): ZoomCreateMeetingResponseDto {
        return zoomWebClient.post()
            .uri(ZoomUrl.API.usersUrl + zoomAdminPropertiesConfiguration.adminId + "/meetings")
            .body(Mono.just(zoomCreateMeetingRequestDto), ZoomCreateMeetingRequestDto::class.java)
            .header(HttpHeaders.AUTHORIZATION, zoomAdminPropertiesConfiguration.authorizationBearerToken)
            .retrieve()
            .bodyToMono(ZoomCreateMeetingResponseDto::class.java)
            .doOnError { error ->
                logger.error("Exception happened:${error.message}")
                throw ZoomClientException(error.message ?: "")
            }
            .block()
            .also { logger.info("Creating Zoom Meeting: $it") } ?: throw RuntimeException()
    }

    override fun deleteZoomMeeting(zoomMeetingId: Long): ResponseEntity<Void> {
        return zoomWebClient.post()
            .uri(ZoomUrl.API.meetingsUrl + zoomMeetingId)
            .header(HttpHeaders.AUTHORIZATION, zoomAdminPropertiesConfiguration.authorizationBearerToken)
            .retrieve()
            .toBodilessEntity()
            .doOnError { error ->
                logger.error("Exception happened:${error.message}")
                throw ZoomClientException(error.message ?: "")
            }
            .block()
            .also { logger.info("Creating Zoom Meeting: $it") } ?: throw RuntimeException()
    }

    private fun createOAuthHeader(): String {
        val base64Params = Base64.getEncoder()
            .encode("${zoomPropertiesConfiguration.clientId}:${zoomPropertiesConfiguration.clientSecret}".toByteArray())
            .toString(Charsets.UTF_8)
        val basicHeader = "Basic $base64Params".also {
            zoomAdminPropertiesConfiguration.authorizationBasicToken = it
            logger.info("Current Basic Token is: ${zoomAdminPropertiesConfiguration.authorizationBasicToken}")
        }
        return basicHeader
    }

    @EventListener(ApplicationReadyEvent::class)
    fun getFirstBearerToken() {
        createOAuthHeader()
    }


}