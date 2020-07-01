package com.mlkitexample.whatami.answer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mlkitexample.whatami.Helper

class AnswerFragmentViewModel: ViewModel() {
    var mutableLiveDataOfListOfElements = MutableLiveData<MutableList<String>>(Helper.mutableList?.map { it.name }?.toMutableList())

    fun popElements(listOfFoundElement: List<String>){
        val listOfElementCache = mutableLiveDataOfListOfElements.value
        val lengthOfElementCacheBeforePop = listOfElementCache?.size
        listOfFoundElement.forEach {
            if(listOfElementCache?.contains(it)==true){
                listOfElementCache.remove(it)
            }
        }
        if(lengthOfElementCacheBeforePop != listOfElementCache?.size){
            mutableLiveDataOfListOfElements.postValue(listOfElementCache)
        }
    }
}