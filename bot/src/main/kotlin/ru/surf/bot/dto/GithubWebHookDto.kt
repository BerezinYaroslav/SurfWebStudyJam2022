package ru.surf.bot.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GithubWebHookDto(@JsonProperty("url") val url: String)
