package com.desabulila.snapcencus.ui.admin.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.adapter.UserListAdapter
import com.desabulila.snapcencus.databinding.ActivityUserListBinding
import com.desabulila.snapcencus.ui.UserViewModelFactory
import com.desabulila.snapcencus.ui.admin.add.AddUserActivity
import com.google.android.material.snackbar.Snackbar

class UserListActivity : AppCompatActivity() {

    private lateinit var userListAdapter: UserListAdapter

    private val binding: ActivityUserListBinding by lazy {
        ActivityUserListBinding.inflate(layoutInflater)
    }

    private val viewModel: UserListViewModel by viewModels {
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

        setupAdapter()
        setupLoading()
        setupSnackbarText()
        setupAction()
    }

    private fun setupAdapter() {
        userListAdapter = UserListAdapter()
        binding.contentUserList.rvDataUser.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }

        viewModel.listUser.observe(this) { result ->
            userListAdapter.submitList(result)
        }

    }

    private fun setupSnackbarText() {
        viewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackbarText ->
                Snackbar.make(window.decorView.rootView, snackbarText, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupLoading() {
        viewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getListUser()
    }

    private fun setupAction() {
        binding.fabAddUser.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.itemDialogLoading.loadingLayout.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }
}