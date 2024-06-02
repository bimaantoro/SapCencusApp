package com.desabulila.snapcencus.ui.auth

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivityPinAuthBinding

class PinAuthActivity : AppCompatActivity() {

    private val binding: ActivityPinAuthBinding by lazy {
        ActivityPinAuthBinding.inflate(layoutInflater)
    }

    private var pin = ""

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
    }

    private fun setupIntent() {
        val role = intent.getStringExtra(EXTRA_ROLE)
        binding.contentPinAuth.itemPinAuth.tvRole.text = role
    }

    companion object {
        const val EXTRA_ROLE = "extra_role"
    }
}