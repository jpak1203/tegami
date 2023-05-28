package com.jpakku.tegami.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() : ViewModel() {

    private var _showFirstTimeUserDialog = MutableLiveData<Boolean>()
    val showFirstTimeUserDialog: LiveData<Boolean>
        get() = _showFirstTimeUserDialog

    private var _showRulesDialog = MutableLiveData<Boolean>()
    val showRulesDialog: LiveData<Boolean>
        get() = _showRulesDialog

    fun showFirstTimeUserDialog(show: Boolean) {
        _showFirstTimeUserDialog.postValue(show)
    }

    fun showRulesDialog(show: Boolean) {
        _showRulesDialog.postValue(show)
    }
}