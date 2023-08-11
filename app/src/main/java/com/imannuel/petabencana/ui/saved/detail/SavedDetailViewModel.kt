package com.imannuel.petabencana.ui.saved.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imannuel.petabencana.data.model.Properties
import com.imannuel.petabencana.data.repository.UrunDayaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedDetailViewModel @Inject constructor(private val urunDayaRepository: UrunDayaRepository) : ViewModel() {

    fun saveUrunDaya(urunDaya: Properties) {
        viewModelScope.launch {
            urunDaya.isSaved = true
            urunDayaRepository.saveUrunDaya(urunDaya)
        }
    }

    fun isUrunDayaExist(id: String) = urunDayaRepository.isUrunDayaExist(id)

    fun deleteUrunDaya(id: String) {
        viewModelScope.launch {
            urunDayaRepository.deleteUrunDaya(id)
        }
    }
}