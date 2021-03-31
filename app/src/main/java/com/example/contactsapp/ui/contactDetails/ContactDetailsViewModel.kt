package com.example.contactsapp.ui.contactDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsapp.data.Contact
import com.example.contactsapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ContactDetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    fun getContactByInt(contactId: Int) = repository.getContactById(contactId)

    private fun deleteRepositoryContact(contact: Contact?){
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }

    fun deleteContact(contact: Contact?){
        deleteRepositoryContact(contact)
    }
}