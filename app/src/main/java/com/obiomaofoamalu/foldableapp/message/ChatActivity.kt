package com.obiomaofoamalu.foldableapp.message

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obiomaofoamalu.foldableapp.R
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.android.ext.android.inject

class ChatActivity : Activity() {

    private val viewModel: ChatViewModel by inject()
    private val messageAdapter: MessageAdapter by lazy {
        MessageAdapter()
    }

    private lateinit var messageRecyclerView: RecyclerView

    private val actionsSubject: PublishSubject<ChatViewAction> = PublishSubject.create()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        setupRecyclerView()
        attachViewModel()
        actionsSubject.onNext(ChatViewAction.LoadMessages(intent.getStringExtra(CONVERSATION_ID)!!))
    }

    private fun setupRecyclerView() {
        messageRecyclerView = findViewById(R.id.message_recycler_view)
        messageRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = messageAdapter
        }
    }

    private fun attachViewModel() {
        disposables += viewModel.stateOf { messages }
            .subscribe { messageAdapter.updateMessages(it) }
        viewModel.attach(actionsSubject)
    }

    companion object {

        private const val CONVERSATION_ID = "CONVERSATION_ID"

        fun launch(activity: Activity, conversationId: String) {
            activity.startActivity(
                Intent(activity, ChatActivity::class.java)
                    .putExtra(CONVERSATION_ID, conversationId)
            )
        }
    }
}
