package com.example.chatsdklib.base.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


open class BaseViewModel : ViewModel(){
    var toastMsg = MutableLiveData<String>()

    /**
     * Listen for toast messages
     */
    fun toastMessagesLiveData(): LiveData<String> = toastMsg
}