package com.desabulila.snapcencus.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.data.Result
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.databinding.ActivityPinAuthBinding
import com.desabulila.snapcencus.ui.UserViewModelFactory
import com.desabulila.snapcencus.ui.admin.main.MainAdminActivity
import com.desabulila.snapcencus.ui.user.main.MainUserActivity
import com.google.android.material.snackbar.Snackbar

class PinAuthActivity : AppCompatActivity() {

    private val binding: ActivityPinAuthBinding by lazy {
        ActivityPinAuthBinding.inflate(layoutInflater)
    }

    private val viewModel: PinAuthViewModel by viewModels {
        UserViewModelFactory.getInstance(this)
    }

    private var pin = ""
    private var role = ""

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
        setupSnackbarText()
        setupIntent()
        setupPostPin()
        setupListener()
    }

    private fun setupLoading() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun setupSnackbarText() {
        viewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                showSnackbarText(snackBarText)
            }
        }
    }

    private fun setupListener() {
        binding.contentPinAuth.itemPinAuth.edtPin.isEnabled = false

        val numberButtons = listOf(
            binding.contentPinAuth.itemPinAuth.btn0, binding.contentPinAuth.itemPinAuth.btn1,
            binding.contentPinAuth.itemPinAuth.btn2, binding.contentPinAuth.itemPinAuth.btn3,
            binding.contentPinAuth.itemPinAuth.btn4, binding.contentPinAuth.itemPinAuth.btn5,
            binding.contentPinAuth.itemPinAuth.btn6, binding.contentPinAuth.itemPinAuth.btn7,
            binding.contentPinAuth.itemPinAuth.btn8, binding.contentPinAuth.itemPinAuth.btn9
        )

        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                pin += index.toString()
                binding.contentPinAuth.itemPinAuth.edtPin.setText(pin)
            }
        }

        binding.contentPinAuth.itemPinAuth.btnClear.setOnClickListener {
            pin += ""
            binding.contentPinAuth.itemPinAuth.edtPin.setText(pin)
        }

        binding.contentPinAuth.itemPinAuth.btnOk.setOnClickListener {
            viewModel.postPin(pin)
        }
    }

    private fun setupPostPin() {
        viewModel.pinResult.observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    val data = result.data
                    handleLoginPinSuccess(data)
                    viewModel.saveSession(data)
                }

                is Result.Error -> {
                    showSnackbarText(result.error)
                }

                is Result.Empty -> {
                    showSnackbarText("Pin tidak boleh kosong")
                }
            }
        }
    }

    private fun handleLoginPinSuccess(user: UserModel) {
        if (user.role != role) {
            showSnackbarText("Login failed")
            return
        }

        val welcomeMessage = "Selamat datang, ${user.name}"
        showSnackbarText(welcomeMessage)

        val intent = when (user.role) {
            "admin" -> Intent(this, MainAdminActivity::class.java)
            else -> Intent(this, MainUserActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

    private fun setupIntent() {
        val role = intent.getStringExtra(EXTRA_ROLE)
        binding.contentPinAuth.itemPinAuth.tvRole.text = role
    }

    private fun showLoading(isLoading: Boolean) {
        binding.itemDialogLoading.loadingLayout.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showSnackbarText(message: String) {
        Snackbar.make(
            window.decorView.rootView,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val EXTRA_ROLE = "extra_role"
    }
}