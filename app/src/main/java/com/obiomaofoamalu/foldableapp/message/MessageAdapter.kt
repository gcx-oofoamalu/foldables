package com.obiomaofoamalu.foldableapp.message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obiomaofoamalu.foldableapp.Message
import com.obiomaofoamalu.foldableapp.R

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private var messages: List<Message> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.message_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun updateMessages(data: List<Message>) {
        messages = data
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val myMessageContainer: View = view.findViewById(R.id.my_message_container)
        private val otherMessageContainer: View = view.findViewById(R.id.other_message_container)
        private val myMessageView: TextView = view.findViewById(R.id.my_message)
        private val otherMessageView: TextView = view.findViewById(R.id.other_message)

        fun bind(message: Message) {
            myMessageContainer.visibility = View.GONE
            otherMessageContainer.visibility = View.GONE

            if (message.isSentByMe) {
                myMessageContainer.visibility = View.VISIBLE
                myMessageView.text = message.text
            } else {
                otherMessageContainer.visibility = View.VISIBLE
                otherMessageView.text = message.text
            }
        }
    }
}
