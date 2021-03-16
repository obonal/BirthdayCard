package org.bonal.birthdaycard.presentation

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.bonal.birthdaycard.domain.FetchBirthdayDataUseCase
import org.bonal.birthdaycard.data.model.*
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    private val fetchBirthdayDataUseCase: FetchBirthdayDataUseCase,
    private val birthdayGuestCardDataMapper: BirthdayGuestCardDataMapper
) : ViewModel() {

    private val _birthdayHost = MutableLiveData<BirthdayHost>()
    val birthdayHost: LiveData<BirthdayHost> = _birthdayHost

    private val _guestList = MutableLiveData<List<BirthdayGuest>>()
    val guestList: LiveData<List<BirthdayGuestCardViewState>> =
        Transformations.map(_guestList) { guests ->
            guests.map { birthdayGuestCardDataMapper.map(it) }
        }

    private val _birthdayCardMessage = MutableLiveData<String>()
    val birthdayCardMessage: LiveData<String> = _birthdayCardMessage

    private val _galleryButtonLabel = MutableLiveData<String>()
    val galleryButtonLabel: LiveData<String> = _galleryButtonLabel

    private val _birthdayCardBackground = MutableLiveData<String>()
    val birthdayCardBackground: LiveData<String> = _birthdayCardBackground

    fun loadBirthdayData() =
        viewModelScope.launch {
            try {
                val birthdayData = fetchBirthdayDataUseCase.fetchBirthdayData()
                _birthdayHost.value = birthdayData.birthdayHost
                _guestList.value = birthdayData.guestList
                _birthdayCardMessage.value = birthdayData.birthdayCardMessage
                _birthdayCardBackground.value = birthdayData.birthdayCardBackground
                _galleryButtonLabel.value = birthdayData.galleryButtonLabel
            } catch (t: Throwable) {
                // TODO: Better error handling + loading too :-)
                _guestList.value = emptyList()
                _birthdayCardMessage.value = t.localizedMessage
            }
        }

}

