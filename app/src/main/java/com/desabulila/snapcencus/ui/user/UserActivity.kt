package com.desabulila.snapcencus.ui.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivityUserBinding
import com.desabulila.snapcencus.ui.user.ktp.result.ResultScanKtpActivity
import com.desabulila.snapcencus.ui.user.ktp.scan.ScanKtpActivity
import com.desabulila.snapcencus.ui.user.list.PendudukListActivity

class UserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        setupAction()
    }

    private fun setupAction() {
        binding.btnScan.setOnClickListener {
            binding.lyContentConfirm.contentConfirm.visibility = View.VISIBLE
        }

        binding.lyContentConfirm.btnYes.setOnClickListener {
            binding.lyContentConfirm.contentConfirm.visibility = View.GONE
            val intent = Intent(this, ScanKtpActivity::class.java)
            startActivity(intent)
        }

        binding.lyContentConfirm.btnNo.setOnClickListener {
            binding.lyContentConfirm.contentConfirm.visibility = View.GONE
            val intent = Intent(this, ResultScanKtpActivity::class.java)
            startActivity(intent)
        }

        binding.btnShowData.setOnClickListener {
            val intent = Intent(this, PendudukListActivity::class.java)
            startActivity(intent)
        }

    }

}