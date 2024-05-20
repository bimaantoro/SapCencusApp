package com.desabulila.snapcencus.ui.user.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivityMainUserBinding
import com.desabulila.snapcencus.ui.user.ktp.result.KtpResultActivity
import com.desabulila.snapcencus.ui.user.ktp.scan.ScanKtpActivity
import com.desabulila.snapcencus.ui.user.list.PendudukListActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainUserActivity : AppCompatActivity() {

    private val binding: ActivityMainUserBinding by lazy {
        ActivityMainUserBinding.inflate(layoutInflater)
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

        setupAction()
    }

    private fun setupAction() {
        binding.btnScanDocument.setOnClickListener {
            MaterialAlertDialogBuilder(this).apply {
                setTitle(R.string.title_ocr)
                setMessage(R.string.message_ocr)
                setPositiveButton(R.string.ya) { _, _ ->
                    val intent = Intent(this@MainUserActivity, ScanKtpActivity::class.java)
                    startActivity(intent)
                }
                setNeutralButton(R.string.input_manual) { _, _ ->
                    val intent = Intent(this@MainUserActivity, KtpResultActivity::class.java)
                    startActivity(intent)
                }
                    .setNegativeButton(R.string.cancel) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }

        binding.btnShowData.setOnClickListener {
            val intent = Intent(this, PendudukListActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {

        }
    }
}