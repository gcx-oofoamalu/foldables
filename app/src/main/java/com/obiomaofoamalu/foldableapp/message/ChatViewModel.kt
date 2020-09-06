package com.obiomaofoamalu.foldableapp.message

import com.obiomaofoamalu.foldableapp.Message
import com.obiomaofoamalu.foldableapp.repository.ConversationsRepository
import com.obiomaofoamalu.mvvm.ViewAction
import com.obiomaofoamalu.mvvm.ViewEffect
import com.obiomaofoamalu.mvvm.ViewModel
import com.obiomaofoamalu.mvvm.ViewState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.ofType
import io.reactivex.rxjava3.kotlin.plusAssign

class ChatViewModel(
    private val repository: ConversationsRepository
) : ViewModel<ChatViewAction, ChatViewState, ChatViewEffect>(ChatViewState()) {

    override fun handleActions(actions: Observable<ChatViewAction>) {
        disposables += actions.ofType<ChatViewAction.LoadMessages>()
            .map { it.conversationId }
            .flatMap { repository.getMessages(it) }
            .subscribe { updateState { copy(messages = it) } }
    }
}

sealed class ChatViewAction : ViewAction {
    data class LoadMessages(val conversationId: String) : ChatViewAction()
}

sealed class ChatViewEffect : ViewEffect

data class ChatViewState(
    val messages: List<Message> = listOf()
) : ViewState