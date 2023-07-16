package com.hmshohrab.commitview.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.hmshohrab.commitview.base.ErrorResponse
import com.hmshohrab.commitview.base.Resource
import com.hmshohrab.commitview.data.source.CommitViewApi
import com.hmshohrab.commitview.paging.ProductPagingSource
import com.hmshohrab.commitview.repository.model.UserModel
import javax.inject.Inject

class CommitViewRepository @Inject constructor(private val apiService: CommitViewApi) {
    val data = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        ProductPagingSource(apiService)
    }.flow


    suspend fun getUserProfile(userName: String): Resource<UserModel, ErrorResponse> {
        val response = apiService.getUserProfile(userName)
        return if (response.isSuccessful) {
            Resource.success(response.body())
        } else {
            Resource.error(
                null,
                error = ErrorResponse(
                    success = false,
                    message = response.message(),
                    code = response.code()
                )
            )
        }
    }
}