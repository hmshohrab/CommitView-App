package com.hmshohrab.commitview.data.source

import com.hmshohrab.commitview.repository.model.CommitModel
import com.hmshohrab.commitview.repository.model.UserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query



interface CommitViewApi {
    @GET("/repos/JetBrains/compose-multiplatform/commits?per_page=20&sort=author-date")
    suspend fun getCommitList(@Query("page") pageNumber: Int): Response<CommitModel>

    @GET("/users/{user}")
    suspend fun getUserProfile(@Path("user") userName:String): Response<UserModel>


}