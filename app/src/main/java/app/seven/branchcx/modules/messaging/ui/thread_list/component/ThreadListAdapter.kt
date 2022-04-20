package app.seven.branchcx.modules.messaging.ui.thread_list.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import app.seven.branchcx.core.utils.DateUtil
import app.seven.branchcx.databinding.MessagingThreadListItemBinding
import app.seven.branchcx.modules.messaging.data.model.MessageThread
import kotlin.math.sign

class ThreadListAdapter: RecyclerView.Adapter<ThreadListAdapter.ThreadListViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<MessageThread>() {
        override fun areItemsTheSame(oldItem: MessageThread, newItem: MessageThread): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MessageThread, newItem: MessageThread): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadListViewHolder {
        val binding = MessagingThreadListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ThreadListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ThreadListViewHolder, position: Int) {
        differ.currentList[position].let { thread ->
            holder.binding.tvSender.text = thread.userId.toString()
            thread.lastMessage?.let { message ->
                holder.binding.tvLastMessagePreview.text = message.body
                holder.binding.tvLastMessageTime.text = message.timestamp.format(DateUtil.formatter).toString()
            }

            holder.binding.root.setOnClickListener {
                onItemClickListener?.let{
                    it(thread.id)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    inner class ThreadListViewHolder(val binding: MessagingThreadListItemBinding): RecyclerView.ViewHolder(binding.root)


    private var onItemClickListener: ((id: Int) -> Unit)? = null

    fun setOnItemClickListener(listener: (Int) -> Unit){
        onItemClickListener = listener
    }
}

class ThreadListDiffCallback: DiffUtil.ItemCallback<MessageThread>() {
    //    val oldList = mutableListOf<MessageThread>()
//    val newList = mutableListOf<MessageThread>()
//
//    override fun getOldListSize(): Int {
//        return oldList.size
//    }
//
//    override fun getNewListSize(): Int {
//        return newList.size
//    }
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition].id == newList[newItemPosition].id
//    }
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition] == newList[newItemPosition]
//    }
    override fun areItemsTheSame(oldItem: MessageThread, newItem: MessageThread): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MessageThread, newItem: MessageThread): Boolean {
        return oldItem == newItem
    }
}