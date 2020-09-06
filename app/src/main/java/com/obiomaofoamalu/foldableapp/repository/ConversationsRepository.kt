package com.obiomaofoamalu.foldableapp.repository

import com.obiomaofoamalu.foldableapp.Conversation
import com.obiomaofoamalu.foldableapp.Message
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import java.util.*

class ConversationsRepository {

    private var conversations = listOf(
        Conversation(
            id = "c1",
            username = "user 1",
            messages = mutableListOf(
                Message(
                    text = "Hello there",
                    time = Date().time,
                    isSentByMe = false
                ),
                Message(
                    text = "Hello user 1",
                    time = Date().time,
                    isSentByMe = true
                )
            )
        ),
        Conversation(
            id = "c2",
            username = "user 2",
            messages = mutableListOf(
                Message(
                    text = "Hey user 2",
                    time = Date().time,
                    isSentByMe = true
                )
            )
        ),
        Conversation(
            id = "c3",
            username = "user 3",
            messages = mutableListOf(
                Message(
                    text = "Hey user 3",
                    time = Date().time,
                    isSentByMe = true
                )
            )
        )
    )

    fun getConversations(): Observable<List<Conversation>> {
        return Observable.just(conversations)
    }

    fun getMessages(conversationId: String): Observable<List<Message>> {
        return Observable.just(
            conversations.first { it.id == conversationId }.messages
        )
    }

    fun updateConversation(conversationId: String, message: Message): Completable {
        return Completable.create { emitter ->
            conversations = conversations.map { conversation ->
                if (conversation.id == conversationId) {
                    conversation.messages.toMutableList().add(message)
                    conversation.copy(
                        messages = conversation.messages
                    )
                } else {
                    conversation
                }
            }
            emitter.onComplete()
        }
    }
}
