package com.desabulila.snapcencus.ui.admin.edit

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.databinding.ActivityEditUserBinding
import com.desabulila.snapcencus.ui.UserViewModelFactory

class EditUserActivity : AppCompatActivity() {

    private val binding: ActivityEditUserBinding by lazy {
        ActivityEditUserBinding.inflate(layoutInflater)
    }

    private val viewModel: EditUserViewModel by viewModels {
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

        setupIntent()
        setupLoading()
        setupEditUser()
    }

    private fun setupEditUser() {
        binding.contentEditUser.btnUpdate.setOnClickListener {
            val name = binding.contentEditUser.edtNamaUser.text.toString()
            val pin = binding.contentEditUser.edtPin.text.toString()
            val repeatPin = binding.contentEditUser.edtRepeatPin.text.toString()

            if (validateInput(name, pin, repeatPin)) {
                val user = UserModel(
                    name = name,
                    pin = pin,
                    docId = intent.getStringExtra(EXTRA_NAME)
                )

                viewModel.updateUser(user)
            }
        }

        viewModel.updateUserResult.observe(this) { user ->
            if (user != null) {
                finish()
            }
        }
    }

    private fun validateInput(name: String, pin: String, repeatPin: String): Boolean {
        when {
            name.isEmpty() -> {
                binding.contentEditUser.edtNamaUser.error = getString(R.string.empty_name_err)
                binding.contentEditUser.edtNamaUser.requestFocus()
                return false
            }

            pin.isEmpty() -> {
                binding.contentEditUser.edtPin.error = getString(R.string.empty_pin_err)
                binding.contentEditUser.edtPin.requestFocus()
                return false
            }

            repeatPin.isEmpty() -> {
                binding.contentEditUser.edtRepeatPin.error = getString(R.string.empty_pin_err)
                binding.contentEditUser.edtRepeatPin.requestFocus()
                return false
            }

            pin != repeatPin -> {
                binding.contentEditUser.edtRepeatPin.error = getString(R.string.pin_not_match_err)
                binding.contentEditUser.edtRepeatPin.requestFocus()
                return false
            }

            else -> return true
        }
    }

    private fun setupIntent() {
        val name = intent.getStringExtra(EXTRA_NAME)
        val pin = intent.getStringExtra(EXTRA_PIN)

        binding.contentEditUser.edtNamaUser.setText(name)
        binding.contentEditUser.edtPin.setText(pin)
    }

    private fun setupLoading() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.itemDialogLoading.loadingLayout.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_DOC_ID = "extra_doc_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PIN = "extra_pin"
    }
}