package com.obiomaofoamalu.foldableapp

import com.obiomaofoamalu.foldableapp.conversation.ConversationListViewModel
import com.obiomaofoamalu.foldableapp.message.ChatViewModel
import com.obiomaofoamalu.foldableapp.repository.ConversationsRepository
import org.koin.dsl.module

val appModule = module {

    factory {
        ConversationsRepository()
    }

    factory {
        ConversationListViewModel(
            repository = get()
        )
    }

    factory {
        ChatViewModel(
            repository = get()
        )
    }
}
