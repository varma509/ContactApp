package com.example.contactsapp.ui.contactsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.contactsapp.data.Contact
import com.example.contactsapp.data.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ContactsListViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    val getAllContacts = repository.getAllContacts()

    fun findContactByName(query: String): LiveData<List<Contact>> {
        return repository.findContactByName(query)
    }

}