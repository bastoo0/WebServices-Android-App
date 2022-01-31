package com.polytech.applicationws.services

import com.google.gson.JsonElement
import com.squareup.moshi.Json
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

interface UserService {

    @POST("/authentification/login")
    suspend fun login (@Body post: LoginPost): Response<ResponsePost>

    @GET("/film/getFilms")
    fun getFilms(@Header("Authorization") token: String?): Deferred<List<FilmProperty>>

    @GET("/acteur/getActeurs")
    fun getActors(@Header("Authorization") token: String?): Deferred<List<ActorProperty>>

    @GET("/user/doorslogs")
    fun getLogs(@Header("Authorization") token: String?): Deferred<List<LogProperty>>

    @Headers("Content-Type: application/json")
    @POST("/acteur/ajout")
    suspend fun addActor(@Header("Authorization") token: String?, @Body body: ActorPost): Response<Void>

    @GET("/acteur/suppressionByNoAct/{noAct}")
    suspend fun deleteActeur(@Header("Authorization") token: String?, @Path("noAct") noAct: Int?): Response<Void>
}

data class LoginPost (
    val nomUtil: String? = null,
    val motPasse: String? = null
)


data class RegisterPost (
    val email: String? = null,
    val password: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val age: Int? = null,
    val country: String? = null
)

data class ResponsePost (
    val token: String
)

data class UserProperty(
    val id: Int? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val country : String? = null,
    val address: String? = null,
    val age: String? = null,
    val gender: String? = null,
    val role: String? = null
)


data class FilmProperty(
    @Json(name = "noFilm")
    val noFilm: Int? = null,
    @Json(name = "titre")
    val titre: String? = null,
    @Json(name = "duree")
    val duree: Int? = null,
    @Json(name = "budget")
    val budget: String? = null,
    @Json(name = "entityRealisateurByNoRea")
    val realisateur: RealisateurProperty? = null,
    @Json(name = "entityCategorieByCodeCat")
    val categorie: CategorieProperty? = null

)

data class RealisateurProperty(
    @Json(name = "noRea")
    val noRea: Int? = null,
    @Json(name = "nomRea")
    val nomRea: String? = null,
    @Json(name = "prenRea")
    val prenRea: String? = null
)

data class CategorieProperty(
    @Json(name = "codeCat")
    val noRea: String? = null,
    @Json(name = "libelleCat")
    val libelleCat: String? = null
)

data class ActorProperty(
    @Json(name = "noAct")
    val noAct: Int? = null,
    @Json(name = "nomAct")
    val nomAct: String? = null,
    @Json(name = "prenAct")
    val prenAct: String? = null,
    @Json(name = "dateNaiss")
    val dateNaiss: Date? = null,
    @Json(name = "dateDeces")
    val dateDeces: Date? = null
)

data class ActorPost(
    val nomAct: String? = null,
    val prenAct: String? = null,
    val dateNaiss: Date? = null,
    val dateDeces: Date? = null
)


data class LockPost(
    val MAC: String? = null,
    val password: String? = null,
    val _id: String? = null
)

data class UnlockDoorPost(
    val MAC: String? = null,
    val password: String? = null
)

data class LogProperty(
    val MAC: String? = null,
    val uid: String? = null,
    val granted: Boolean? = null,
    val formattedDate: String? = null
)

data class MessageResp(
    val message: String? = null
)