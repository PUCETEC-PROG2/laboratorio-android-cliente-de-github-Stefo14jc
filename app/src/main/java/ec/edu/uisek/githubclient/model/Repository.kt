package ec.edu.uisek.githubclient.model

import java.io.Serializable



data class Repository(
    val id: Int? = null,
    val name: String,
    val description: String?,
    val language: String?,
    val owner: Owner? = null
) : Serializable

data class Owner(
    val login: String,
    val avatar_url: String
)
