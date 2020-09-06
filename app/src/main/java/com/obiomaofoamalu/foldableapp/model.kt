package com.obiomaofoamalu.foldableapp

data class Message(val text: String, val time: Long, val isSentByMe: Boolean)

data class Conversation(val id: String, val username: String, val messages: List<Message>)