package com.desabulila.snapcencus.data.local.pref

data class UserModel(
    var docId: String,
    val pin: String,
    val name: String,
    val role: String,
    val uId: String,
    val isLogin: Boolean = false
)