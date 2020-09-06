package com.obiomaofoamalu.foldableapp.conversation

import com.obiomaofoamalu.foldableapp.repository.ConversationsRepository
import com.obiomaofoamalu.mvvm.ViewAction
import com.obiomaofoamalu.mvvm.ViewEffect
import com.obiomaofoamalu.mvvm.ViewModel
import com.obiomaofoamalu.mvvm.ViewState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.kotlin.plusAssign

class ConversationListViewModel(
    private val repository: ConversationsRepository
) : ViewModel<ConversationListViewAction, ConversationListViewState, ConversationListViewEffect>(
    ConversationListViewState()
) {

    override fun onAttach() {
        disposables += repository.getConversations()
            .map { conversations ->
                conversations.map { conversation ->
                    ConversationListViewData(
                        id = conversation.id,
                        username = conversation.username,
                        lastMessage = conversation.messages.last().text
                    )
                }
            }
            .subscribe { result -> updateState { copy(conversations = result) } }
    }

    override fun handleActions(actions: Observable<ConversationListViewAction>) {
        disposables += actions.ofType<ConversationListViewAction.ConversationListClicked>()
            .map { it.id }
            .subscribe {
                sendEffect(ConversationListViewEffect.OpenConversationList(it))
            }
    }
}

sealed class ConversationListViewAction : ViewAction {
    data class ConversationListClicked(val id: String) : ConversationListViewAction()
}

sealed class ConversationListViewEffect : ViewEffect {
    data class OpenConversationList(val conversationId: String) : ConversationListViewEffect()
}

data class ConversationListViewState(
    val conversations: List<ConversationListViewData> = listOf()
) : ViewState