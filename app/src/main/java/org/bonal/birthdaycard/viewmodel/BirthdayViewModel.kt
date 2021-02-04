package org.bonal.birthdaycard.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bonal.birthdaycard.data.BirthdayRepository
import org.bonal.birthdaycard.model.*
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository,
    private val birthdayGuestCardDataMapper: BirthdayGuestCardDataMapper
) : ViewModel() {

    private val _birthdayHost = MutableLiveData<BirthdayHost>()
    val birthdayHost: LiveData<BirthdayHost> = _birthdayHost

    private val _guestList = MutableLiveData<List<BirthdayGuest>>()
    val guestList: LiveData<List<BirthdayGuestCardModel>> =
        Transformations.map(_guestList) { guests ->
            guests.map { birthdayGuestCardDataMapper.map(it) }
        }

    private val _birthdayCardMessage = MutableLiveData<String>()
    val birthdayCardMessage: LiveData<String> = _birthdayCardMessage

    private val _birthdayCardBackground = MutableLiveData<String>()
    val birthdayCardBackground: LiveData<String> = _birthdayCardBackground

    fun loadBirthdayData() =
        viewModelScope.launch {
            try {
                val birthdayData = birthdayRepository.getBirthdayData()
                _birthdayHost.value = birthdayData.birthdayHost
                _guestList.value = birthdayData.guestList
                _birthdayCardMessage.value = birthdayData.birthdayCardMessage
                _birthdayCardBackground.value = birthdayData.birthdayCardBackground
            } catch (t: Throwable) {
                // TODO: Better error handling + loading too :-)
                _guestList.value = emptyList()
                _birthdayCardMessage.value = t.localizedMessage
            }
        }

}

