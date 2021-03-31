package com.example.contactsapp.ui.addContact
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.MainActivity
import com.example.contactsapp.R
import com.example.contactsapp.data.Contact
import com.example.contactsapp.databinding.FragmentAddContactBinding
import com.example.contactsapp.util.OPTIONS
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactFragment : Fragment() {

    private val REQUEST_IMAGE = 100
    var profilePicturePath: String? = null

    private lateinit var  binding: FragmentAddContactBinding

    private val viewModel: AddContactViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_contact, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner

        val args = AddContactFragmentArgs.fromBundle(requireArguments())

        if (args.id != -1){
            val contact = viewModel.getContactById(args.id)
            contact.observe(viewLifecycleOwner, {
                binding.contact = it
            })
        }

        binding.firstNameEditText.doAfterTextChanged {
            viewModel.firstName = it.toString()
        }

        binding.lastNameEditText.doAfterTextChanged {
            viewModel.lastName = it.toString()
        }

        binding.phoneNumberEditText.doAfterTextChanged {
            viewModel.phoneNumber = it.toString()
        }

        binding.emailEditText.doAfterTextChanged {
            viewModel.email = it.toString()
        }

        binding.uploadProfilePictureImageView.setOnClickListener{
            selectProfilePicture()
        }

        binding.saveButton.setOnClickListener {
            if (args.id == -1) {
                viewModel.saveContact(
                    Contact(
                    viewModel.firstName,
                    viewModel.lastName,
                    viewModel.phoneNumber,
                    viewModel.email,
                    profilePicturePath)
                )
                it.findNavController().navigate(AddContactFragmentDirections
                    .actionAddContactFragmentToContactsFragment())
            } else {
                viewModel.updateContact(
                    Contact(
                    viewModel.firstName,
                    viewModel.lastName,
                    viewModel.phoneNumber,
                    viewModel.email,
                    profilePicturePath,
                    args.id)
                )

                it.findNavController().navigate(AddContactFragmentDirections
                    .actionAddContactFragmentToContactsFragment())
            }
        }

        binding.cancelButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner)
        {
            // handling back button
            findNavController().navigate(AddContactFragmentDirections.actionAddContactFragmentToContactsFragment(), OPTIONS)
        }

        return binding.root
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun selectProfilePicture(){
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null){
            startActivityForResult(intent, REQUEST_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK){
            val imageUri = data?.data

            profilePicturePath = imageUri.toString()
            binding.uploadProfilePictureImageView.setImageURI(data?.data)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Add New Contact"
    }
}
