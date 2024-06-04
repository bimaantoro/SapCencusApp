package com.desabulila.snapcencus.ui.admin.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivityMainAdminBinding
import com.desabulila.snapcencus.ui.UserViewModelFactory
import com.desabulila.snapcencus.ui.admin.list.UserListActivity
import com.desabulila.snapcencus.ui.home.HomeActivity

class MainAdminActivity : AppCompatActivity() {

    private val binding: ActivityMainAdminBinding by lazy {
        ActivityMainAdminBinding.inflate(layoutInflater)
    }

    private val viewModel: MainAdminViewModel by viewModels {
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

        setupAction()
        setupView()
    }

    private fun setupView() {
        viewModel.getSession().observe(this) {
            binding.tvSubHeadline.text = it.name
        }
    }

    private fun setupAction() {
        binding.btnShowDataUser.setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}