package com.example.contactsapp.ui.contactsList

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.MainActivity
import com.example.contactsapp.R
import com.example.contactsapp.databinding.FragmentContactsListBinding
import com.example.contactsapp.util.OPTIONS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactsListFragment : Fragment() {


    private  val viewModel: ContactsListViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentContactsListBinding.inflate(inflater, container, false)


        val adapter = ContactsAdapter(requireContext(), ContactsClickListener {
            val id = it.id
            this.findNavController().navigate(ContactsListFragmentDirections
                .actionContactsListFragmentToContactDetailsFragment(id))
        })

        binding.contactList.adapter = adapter

        subscribeUI(adapter)

        binding.fab.setOnClickListener {
            it.findNavController().navigate(R.id.action_contactsListFragment_to_addContactFragment, null, OPTIONS)
        }

        return binding.root

    }

    private fun subscribeUI(adapter: ContactsAdapter) {
        viewModel.getAllContacts.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Contact Book"
    }
}
