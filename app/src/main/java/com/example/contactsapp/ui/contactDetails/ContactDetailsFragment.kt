package com.example.contactsapp.ui.contactDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.MainActivity
import com.example.contactsapp.R
import com.example.contactsapp.databinding.FragmentContactDetailsBinding
import com.example.contactsapp.util.OPTIONS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailsFragment : Fragment() {

    private  val viewModel: ContactDetailsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentContactDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner

        val args = ContactDetailsFragmentArgs.fromBundle(requireArguments())
        val contact = viewModel.getContactByInt(args.id)

        contact.observe(viewLifecycleOwner, {
            binding.data.contact = it
        })

        binding.editContactFab.setOnClickListener {
            it.findNavController().navigate(
                ContactDetailsFragmentDirections
                    .actionContactDetailsFragmentToAddContactFragment(args.id)
            )
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteContact(binding.data.contact)
            it.findNavController().navigate(
                ContactDetailsFragmentDirections
                    .actionContactDetailsFragmentToContactsListFragment()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner)
        {
            // handling back button
            findNavController().navigate(ContactDetailsFragmentDirections.actionContactDetailsFragmentToContactsListFragment(), OPTIONS)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Contact Details"
    }

}