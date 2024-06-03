package com.desabulila.snapcencus.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.desabulila.snapcencus.R
import com.desabulila.snapcencus.databinding.ActivityHomeBinding
import com.desabulila.snapcencus.ui.auth.PinAuthActivity

class HomeActivity : AppCompatActivity() {

    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
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

        setupSpinner()
    }

    private fun setupSpinner() {
        val roleArray = resources.getStringArray(R.array.role)
        val adapter =
            object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roleArray) {
                override fun isEnabled(position: Int): Boolean {
                    return position != 0
                }

                override fun getDropDownView(
                    position: Int,
                    convertView: View?,
                    parent: ViewGroup
                ): View {
                    val view = super.getDropDownView(position, convertView, parent) as TextView
                    view.setTextColor(if (position == 0) Color.GRAY else Color.BLACK)
                    return view
                }
            }

        binding.spinRole.adapter = adapter
        binding.spinRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            private var selectedRole = ""

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0 && selectedRole != roleArray[position]) {
                    val role = roleArray[position]
                    val intent = Intent(this@HomeActivity, PinAuthActivity::class.java)
                    intent.putExtra(PinAuthActivity.EXTRA_ROLE, role)
                    startActivity(intent)
                    selectedRole = role
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}