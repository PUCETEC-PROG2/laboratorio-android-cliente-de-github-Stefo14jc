package ec.edu.uisek.githubclient.network
// .

import ec.edu.uisek.githubclient.model.Repository
import ec.edu.uisek.githubclient.model.Owner
import retrofit2.Call
import retrofit2.http.*

interface GitHubService {

    @GET("user/repos")
    fun getUserRepos(
        @Header("Authorization") token: String
    ): Call<List<Repository>>

    @GET("user")
    fun getAuthenticatedUser(
        @Header("Authorization") token: String
    ): Call<Owner>

    @POST("user/repos")
    fun createRepository(
        @Header("Authorization") token: String,
        @Body repo: Map<String, String>
    ): Call<Repository>

    @PATCH("repos/{owner}/{repo}")
    fun updateRepository(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body body: Map<String, String>
    ): Call<Repository>

    @DELETE("repos/{owner}/{repo}")
    fun deleteRepository(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): Call<Void>
}
