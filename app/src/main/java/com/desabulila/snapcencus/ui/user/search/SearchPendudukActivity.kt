package com.desabulila.snapcencus.ui.user.search

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivitySearchPendudukBinding
import com.desabulila.snapcencus.ui.user.detail.DetailPendudukActivity

class SearchPendudukActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPendudukBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchPendudukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupAction()
    }

    private fun setupAction() {

        val nik = intent.getStringExtra("nik")
        binding.edtNikQuery.setText(nik.toString())


        binding.btnSearch.setOnClickListener {
            val intent = Intent(this, DetailPendudukActivity::class.java).apply {
                putExtra(EXTRA_NIK_RESULT, nik)
            }

            startActivity(intent)
        }
    }

    companion object {
        const val EXTRA_NIK_RESULT = "extra_nik_result"
    }
}