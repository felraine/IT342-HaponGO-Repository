package com.example.hapongo.network

import com.example.hapongo.model.Dictionary
import com.example.hapongo.model.Lesson
import com.example.hapongo.model.User
import com.example.hapongo.model.LessonContent
import com.example.hapongo.model.LessonQuizResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users/admin/get_all_users")
    suspend fun getAllUsers(): Response<List<User>>

    // Login user
    @FormUrlEncoded
    @POST("users/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<User>

    // Register user
    @POST("users/register")
    suspend fun registerUser(
        @Body user: User
    ): Response<User>

    // Get user by email
    @GET("users/{email}")
    suspend fun getUserByEmail(
        @Path("email") email: String
    ): Response<User>

    // Get user by ID (admin only)
    @GET("users/admin/get_user/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): Response<User>

    // Update user
    @PUT("users/update_user/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: User
    ): Response<User>

    // Delete user
    @DELETE("users/delete_user/{id}")
    suspend fun deleteUser(
        @Path("id") id: Int
    ): Response<String>


    // ---------Dictionary Endpoints-----------
    @GET("dictionary/getTenWords")
    suspend fun getTop10Words(): Response<List<Dictionary>>

    @GET("dictionary/{id}")
    suspend fun getWordById(
        @Path("id") id: Int
    ): Response<Dictionary>

    @POST("dictionary/{id}")
    suspend fun createWord(
        @Body word: Dictionary
    ): Response<Dictionary>

    @PUT("dictionary/{id}")
    suspend fun updateWord(
        @Path("id") id: Int,
        @Body word: Dictionary
    ): Response<Dictionary>

    @DELETE("dictionary/{id}")
    suspend fun deleteWord(
        @Path("id") id: Int
    ): Response<String>

    // Search for a word in the dictionary
    @GET("dictionary/search")
    suspend fun searchDictionary(
        @Query("searchTerm") searchTerm: String
    ): Response<List<Dictionary>>

    // ----------Homepage------------
    @GET("lessons")
    suspend fun getAllLessons(): Response<List<Lesson>>

    @GET("lesson-contents/lesson/{lessonId}")
    suspend fun getLessonContentsByLessonId(
        @Path("lessonId") lessonId: Long
    ): Response<List<LessonContent>>

    // ----------Quiz------------
    @GET("lesson-quizzes/lesson/{lessonId}")
    suspend fun getQuizzesByLessonId(@Path("lessonId") lessonId: Long): Response<List<LessonQuizResponse>>
}