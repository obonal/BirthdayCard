package org.bonal.birthdaycard.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bonal.birthdaycard.data.BirthdayRepository
import org.bonal.birthdaycard.model.*
import javax.inject.Inject

@HiltViewModel
class BirthdayMemoriesViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository) : ViewModel() {

    private val _birthdayHost = MutableLiveData<BirthdayHost>()
    val birthdayHost: LiveData<BirthdayHost> = _birthdayHost

    private val _memoriesList = MutableLiveData<List<BirthdayMemory>>()
    val memoriesList: LiveData<List<BirthdayMemory>> = _memoriesList

    fun loadMemories() =
        viewModelScope.launch {
            try {
                _memoriesList.value = birthdayRepository.getMemories()
            } catch (t: Throwable) {
                // TODO: Error handling
            }
        }

}

