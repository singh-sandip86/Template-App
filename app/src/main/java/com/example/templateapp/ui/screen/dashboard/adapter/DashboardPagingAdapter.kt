package com.example.templateapp.ui.screen.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.templateapp.databinding.DashboardListItemBinding
import com.example.templateapp.dto.response.dashboard.Transaction
import com.example.templateapp.dto.response.dashboard.toTransaction

class DashboardPagingAdapter(private val onItemClick: (transaction: Transaction) -> Unit) :
    PagingDataAdapter<Transaction, DashboardPagingAdapter.DashboardViewHolder>(COMPARATOR) {

    inner class DashboardViewHolder(private val binding: DashboardListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(transaction: Transaction) {
            binding.transaction = transaction.toTransaction()
            binding.setOnViewTransactionClick { onItemClick.invoke(transaction) }
        }
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        getItem(position)?.let { transactionItem ->
            holder.bind(transactionItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = DashboardListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DashboardViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem == newItem
            }
        }
    }
}