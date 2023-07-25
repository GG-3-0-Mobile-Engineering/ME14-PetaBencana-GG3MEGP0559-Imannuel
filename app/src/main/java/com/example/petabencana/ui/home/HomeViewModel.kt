package com.example.petabencana.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petabencana.data.model.UrunDaya
import com.example.petabencana.data.repository.UrunDayaRepository
import com.example.petabencana.utils.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val urunDayaRepository: UrunDayaRepository): ViewModel() {

    private var _result = MutableLiveData<Resource<UrunDaya>>()
    val result : LiveData<Resource<UrunDaya>> = _result

    init {
        getUrunDaya()
    }

    private fun getUrunDaya(){
        viewModelScope.launch {
            urunDayaRepository.getUrunDaya().collect{
                _result.postValue(it)
            }
        }
    }

}