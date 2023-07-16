package com.hmshohrab.commitview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hmshohrab.commitview.base.ErrorResponse
import com.hmshohrab.commitview.base.Resource
import com.hmshohrab.commitview.repository.CommitViewRepository
import com.hmshohrab.commitview.repository.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CommitViewModel @Inject constructor(private val commitViewRepo: CommitViewRepository) :
    ViewModel() {
    val commitViewData = commitViewRepo.data


    private var _userModelResult = MutableLiveData<Resource<UserModel, ErrorResponse>>()
    var userModelResult: LiveData<Resource<UserModel, ErrorResponse>> = _userModelResult

    fun getUserProfile(userName: String) {
        _userModelResult.postValue(Resource.loading(null))
        viewModelScope.launch {
            _userModelResult.postValue(commitViewRepo.getUserProfile(userName))
        }
    }

}

