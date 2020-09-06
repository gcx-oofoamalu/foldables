package com.obiomaofoamalu.foldableapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obiomaofoamalu.foldableapp.conversation.ConversationAdapter
import com.obiomaofoamalu.foldableapp.conversation.ConversationListViewAction
import com.obiomaofoamalu.foldableapp.conversation.ConversationListViewEffect
import com.obiomaofoamalu.foldableapp.conversation.ConversationListViewModel
import com.obiomaofoamalu.foldableapp.message.ChatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.plusAssign
import io.reactivex.rxjava3.subjects.PublishSubject
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), ConversationAdapter.OnClickListener {

    private val viewModel: ConversationListViewModel by inject()
    private val conversationsAdapter: ConversationAdapter by lazy {
        ConversationAdapter(this)
    }

    private lateinit var conversationRecyclerView: RecyclerView
    private val actionsSubject: PublishSubject<ConversationListViewAction> = PublishSubject.create()
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        attachViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.detach()
        actionsSubject.onComplete()
        disposables.dispose()
    }

    override fun onConversationClicked(conversationId: String) {
        actionsSubject.onNext(ConversationListViewAction.ConversationListClicked(conversationId))
    }

    private fun setupRecyclerView() {
        conversationRecyclerView = findViewById(R.id.conversation_list_view)
        conversationRecyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = conversationsAdapter
            addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        }
    }

    private fun attachViewModel() {
        disposables += viewModel.stateOf { conversations }
            .subscribe {
                conversationsAdapter.updateConversations(it)
            }
        disposables += viewModel.effectsOfType<ConversationListViewEffect.OpenConversationList>()
            .map { it.conversationId }
            .subscribe {
                openConversation(it)
            }
        viewModel.attach(actionsSubject)
    }

    private fun openConversation(id: String) {
        ChatActivity.launch(this, id)
    }
}
