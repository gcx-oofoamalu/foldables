package com.obiomaofoamalu.foldableapp.conversation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obiomaofoamalu.foldableapp.R

class ConversationAdapter(
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<ConversationAdapter.ViewHolder>() {

    private var conversations: List<ConversationListViewData> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.conversation_item, parent, false)
        return ViewHolder(view, onClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(conversations[position])
    }

    override fun getItemCount(): Int {
        return conversations.size
    }

    fun updateConversations(data: List<ConversationListViewData>) {
        conversations = data
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val view: View,
        private val onClickListener: OnClickListener
    ) : RecyclerView.ViewHolder(view) {
        private val userNameView: TextView = view.findViewById(R.id.user_name)
        private val lastMessageView: TextView = view.findViewById(R.id.last_message)

        fun bind(conversation: ConversationListViewData) {
            view.setOnClickListener { onClickListener.onConversationClicked(conversation.id) }
            lastMessageView.text = conversation.lastMessage
            userNameView.text = conversation.username
        }
    }

    interface OnClickListener {
        fun onConversationClicked(conversationId: String)
    }
}
