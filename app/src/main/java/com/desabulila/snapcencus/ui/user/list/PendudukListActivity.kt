package com.desabulila.snapcencus.ui.user.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.adapter.PendudukListAdapter
import com.desabulila.snapcencus.data.Result
import com.desabulila.snapcencus.databinding.ActivityPendudukListBinding
import com.desabulila.snapcencus.ui.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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


        val adapter = PendudukListAdapter()
        binding.contentPendudukList.rvData.adapter = adapter
        binding.contentPendudukList.rvData.layoutManager = LinearLayoutManager(this)
        binding.contentPendudukList.rvData.setHasFixedSize(true)

        fetchDataPenduduk(adapter)

        setupAction()
    }

    private fun fetchDataPenduduk(pendudukListAdapter: PendudukListAdapter) {
        viewModel.getPendudukList()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pendudukListResult.collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            binding.apply {
                                contentLoading.progressBarLayout.visibility = View.GONE
                                fabSearch.visibility = View.VISIBLE
                                contentPendudukList.contentPendudukList.visibility = View.VISIBLE
                                contentErrorMessage.tvErrorMessage.visibility = View.GONE
                            }
                            val pendudukData = result.data?.dataPenduduk
                            pendudukListAdapter.submitList(pendudukData)
                        }

                        is Result.Error -> {
                            binding.apply {
                                contentLoading.progressBarLayout.visibility = View.GONE
                                fabSearch.visibility = View.GONE
                                contentPendudukList.contentPendudukList.visibility = View.GONE

                                contentErrorMessage.tvErrorMessage.apply {
                                    text = result.error?.message
                                    visibility = View.VISIBLE
                                }
                            }
                        }

                        is Result.Loading -> {
                            binding.apply {
                                contentLoading.progressBarLayout.visibility = View.VISIBLE
                                fabSearch.visibility = View.GONE
                                contentPendudukList.contentPendudukList.visibility = View.GONE
                                contentErrorMessage.tvErrorMessage.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setupAction() {}
}