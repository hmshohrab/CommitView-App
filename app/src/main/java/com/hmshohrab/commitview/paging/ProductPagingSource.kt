package com.hmshohrab.commitview.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hmshohrab.commitview.data.source.CommitViewApi
import com.hmshohrab.commitview.repository.model.CommitModelItem
import retrofit2.HttpException
import java.io.IOException

class ProductPagingSource(
    private val apiService: CommitViewApi
) : PagingSource<Int, CommitModelItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommitModelItem> {
        val page = params.key ?: 0
        val position = params.key ?: 1
        val limit = 20
        return try {
            val response = apiService.getCommitList(position)

            val nextKey = if (response.body().isNullOrEmpty()) null else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                if (params.loadSize == 3 * limit) {
                    position + 1
                } else {
                    position + (params.loadSize / limit)
                }
            }//response.nextPageToken
            val prevKey = if (position == 1) null else position - 1

            LoadResult.Page(
                data = response.body() ?: mutableListOf(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
        /*   return try {
               val entities = dao.getProductList(params.loadSize, page * params.loadSize)

               // simulate page loading
               if (page != 0) delay(1000)

               LoadResult.Page(
                   data = entities,
                   prevKey = if (page == 0) null else page - 1,
                   nextKey = if (entities.isEmpty()) null else page + 1
               )
           } catch (e: Exception) {
               LoadResult.Error(e)
           }*/
    }

    override fun getRefreshKey(state: PagingState<Int, CommitModelItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
