package ru.surf.defence.service

interface StrategyService {

    fun consumeEvent(event: Any): Any

}