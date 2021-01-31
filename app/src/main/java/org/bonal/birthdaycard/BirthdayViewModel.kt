package org.bonal.birthdaycard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.bonal.birthdaycard.component.GuestCardAction
import org.bonal.birthdaycard.data.BirthdayRepository
import org.bonal.birthdaycard.model.*
import javax.inject.Inject

@HiltViewModel
class BirthdayViewModel @Inject constructor(
    private val birthdayRepository: BirthdayRepository,
    private val messageSender: MessageSender
) : ViewModel() {

    private val _birthdayHost = MutableLiveData<BirthdayHost>()
    val birthdayHost: LiveData<BirthdayHost> = _birthdayHost

    private val _guestList = MutableLiveData<List<BirthdayGuest>>()
    val guestList: LiveData<List<BirthdayGuest>> = _guestList

    private val _birthdayCardMessage = MutableLiveData<String>()
    val birthdayCardMessage: LiveData<String> = _birthdayCardMessage

    fun loadBirthdayData() {
        val birthdayData = birthdayRepository.getBirtdayData()
        _birthdayHost.value = birthdayData.birthdayHost
        _guestList.value = birthdayData.guestList
        _birthdayCardMessage.value = birthdayData.birthdayCardMessage
    }

    fun getSecondaryGuestCardAction(guest: BirthdayGuest): GuestCardAction? =
        guest.phoneNumber?.let { number ->
            GuestCardAction(messageSender.messengerLabel) {
                messageSender.sendMessage(number)
            }
        }
}

