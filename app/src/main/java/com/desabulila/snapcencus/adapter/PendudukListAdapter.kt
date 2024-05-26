package com.desabulila.snapcencus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.desabulila.snapcencus.data.model.PendudukModel
import com.desabulila.snapcencus.databinding.ItemDataBinding
import com.desabulila.snapcencus.ui.user.detail.DetailPendudukActivity
import com.desabulila.snapcencus.utils.EXTRA_RESULT_NIK

class PendudukListAdapter :
    ListAdapter<PendudukModel, PendudukListAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ItemDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: PendudukModel) {
            binding.tvNama.text = data.nama
            binding.tvNik.text = data.nik

            binding.btnEdit.setOnClickListener {
                val intent = Intent(itemView.context, DetailPendudukActivity::class.java)
                intent.putExtra(EXTRA_RESULT_NIK, data.nik)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PendudukModel>() {
            override fun areItemsTheSame(
                oldItem: PendudukModel,
                newItem: PendudukModel
            ): Boolean = oldItem.nik == newItem.nik

            override fun areContentsTheSame(
                oldItem: PendudukModel,
                newItem: PendudukModel
            ): Boolean = oldItem == newItem

        }
    }
}