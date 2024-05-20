package com.desabulila.snapcencus.ui.user.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.adapter.PendudukListAdapter
import com.desabulila.snapcencus.data.ResultState
import com.desabulila.snapcencus.databinding.ActivityPendudukListBinding
import com.desabulila.snapcencus.ui.ViewModelFactory

class PendudukListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPendudukListBinding

    private val viewModel by viewModels<PendudukListViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPendudukListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()


        val pendudukListAdapter = PendudukListAdapter()
        binding.contentPendudukList.rvData.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = pendudukListAdapter
        }

        fetchData(pendudukListAdapter)


    }

    private fun fetchData(pendudukListAdapter: PendudukListAdapter) {
        viewModel.getPendudukList()

        viewModel.pendudukListResult.observe(this) { resultState ->
            when (resultState) {
                is ResultState.Loading -> {
                    binding.apply {
                        contentLoading.progressBarLayout.visibility = View.VISIBLE
                        fabSearch.visibility = View.GONE
                        contentPendudukList.contentPendudukList.visibility = View.GONE
                        contentErrorMessage.tvErrorMessage.visibility = View.GONE
                    }
                }

                is ResultState.Error -> {
                    val error = resultState.error

                    binding.apply {
                        contentLoading.progressBarLayout.visibility = View.GONE
                        fabSearch.visibility = View.GONE
                        contentPendudukList.contentPendudukList.visibility = View.GONE

                        contentErrorMessage.tvErrorMessage.apply {
                            text = error
                            visibility = View.VISIBLE
                        }

                        showToast("Terjadi kesalahan: $error")
                    }
                }

                is ResultState.Success -> {
                    binding.apply {
                        contentLoading.progressBarLayout.visibility = View.GONE
                        fabSearch.visibility = View.VISIBLE
                        contentPendudukList.contentPendudukList.visibility = View.VISIBLE
                        contentErrorMessage.tvErrorMessage.visibility = View.GONE
                    }
                    val pendudukData = resultState.data.dataPenduduk
                    pendudukListAdapter.submitList(pendudukData)
                }
            }
        }
    }

    private fun setupAction() {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}