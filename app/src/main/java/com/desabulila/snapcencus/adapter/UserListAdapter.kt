package com.desabulila.snapcencus.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.databinding.ItemUserBinding
import com.desabulila.snapcencus.ui.admin.edit.EditUserActivity

class UserListAdapter : ListAdapter<UserModel, UserListAdapter.UserViewHolder>(DIFF_CALLBACK) {
    class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserModel) {
            binding.tvNameUser.text = data.name

            binding.btnEditUser.setOnClickListener {
                val intent = Intent(itemView.context, EditUserActivity::class.java)
                intent.putExtra(EditUserActivity.EXTRA_DOC_ID, data.docId)
                intent.putExtra(EditUserActivity.EXTRA_NAME, data.name)
                intent.putExtra(EditUserActivity.EXTRA_PIN, data.pin)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserModel>() {
            override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                oldItem.uId == newItem.uId

            override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean =
                oldItem == newItem
        }
    }
}