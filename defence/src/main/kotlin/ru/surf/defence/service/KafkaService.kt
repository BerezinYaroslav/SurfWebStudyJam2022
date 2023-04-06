package ru.surf.defence.service

interface KafkaService {

    fun sendCoreEvent(event: Any)

}