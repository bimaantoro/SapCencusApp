package com.desabulila.snapcencus.ui.admin.add

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.databinding.ActivityAddUserBinding
import com.desabulila.snapcencus.ui.UserViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AddUserActivity : AppCompatActivity() {

    private val binding: ActivityAddUserBinding by lazy {
        ActivityAddUserBinding.inflate(layoutInflater)
    }

    private val viewModel: AddUserViewModel by viewModels {
        UserViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupLoading()
        setupSnackbartext()
        setupAddUser()
    }

    private fun setupLoading() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setupSnackbartext() {
        viewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackbarText ->
                Snackbar.make(window.decorView.rootView, snackbarText, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupAddUser() {
        binding.contentAddUser.btnSave.setOnClickListener {
            val name = binding.contentAddUser.edtNamaUser.text.toString()
            val pin = binding.contentAddUser.edtPin.text.toString()
            val repeatPin = binding.contentAddUser.edtRepeatPin.text.toString()

            if (validateInput(name, pin, repeatPin)) {
                val user = UserModel(
                    name = name,
                    pin = pin
                )
                viewModel.addUser(user)
            }
        }


        viewModel.addUserResult.observe(this) { user ->
            if (user != null) {
                finish()
            }
        }
    }

    private fun validateInput(name: String, pin: String, repeatPin: String): Boolean {
        when {
            name.isEmpty() -> {
                binding.contentAddUser.edtNamaUser.error = getString(R.string.empty_name_err)
                binding.contentAddUser.edtNamaUser.requestFocus()
                return false
            }

            pin.isEmpty() -> {
                binding.contentAddUser.edtPin.error = getString(R.string.empty_pin_err)
                binding.contentAddUser.edtPin.requestFocus()
                return false
            }

            repeatPin.isEmpty() -> {
                binding.contentAddUser.edtRepeatPin.error = getString(R.string.empty_pin_err)
                binding.contentAddUser.edtRepeatPin.requestFocus()
                return false
            }

            pin != repeatPin -> {
                binding.contentAddUser.edtRepeatPin.error = getString(R.string.pin_not_match_err)
                binding.contentAddUser.edtRepeatPin.requestFocus()
                return false
            }

            else -> return true
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.itemDialogLoading.loadingLayout.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}