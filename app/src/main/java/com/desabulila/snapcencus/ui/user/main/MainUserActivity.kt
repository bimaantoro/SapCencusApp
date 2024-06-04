package com.desabulila.snapcencus.ui.user.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivityMainUserBinding
import com.desabulila.snapcencus.ui.UserViewModelFactory
import com.desabulila.snapcencus.ui.home.HomeActivity
import com.desabulila.snapcencus.ui.user.list.PendudukListActivity
import com.desabulila.snapcencus.ui.user.ocr.ktp.KtpOcrActivity
import com.desabulila.snapcencus.ui.user.ocr.result.ResultKtpOcrActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainUserActivity : AppCompatActivity() {

    private val binding: ActivityMainUserBinding by lazy {
        ActivityMainUserBinding.inflate(layoutInflater)
    }

    private val viewModel: MainUserViewModel by viewModels {
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

        setupView()
        setupAction()
    }

    private fun setupView() {
        viewModel.getSession().observe(this) {
            binding.tvSubHeadline.text = it.name
        }
    }

    private fun setupAction() {
        binding.btnScanDocument.setOnClickListener {
            showDialog()
        }

        binding.btnShowData.setOnClickListener {
            val intent = Intent(this, PendudukListActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this).apply {
            setTitle(R.string.title_ocr)
            setMessage(R.string.message_ocr)
            setPositiveButton(R.string.positive_button_ocr) { _, _ ->
                val intent = Intent(this@MainUserActivity, KtpOcrActivity::class.java)
                startActivity(intent)
            }

            setNeutralButton(R.string.neutral_button_ocr) { _, _ ->
                val intent = Intent(this@MainUserActivity, ResultKtpOcrActivity::class.java)
                startActivity(intent)
            }
                .create()
                .show()
        }
    }
}