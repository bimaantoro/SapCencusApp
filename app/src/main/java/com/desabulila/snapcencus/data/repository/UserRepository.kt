package com.desabulila.snapcencus.data.repository

import com.desabulila.snapcencus.data.Result
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.data.local.pref.UserPreference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class UserRepository private constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {

    suspend fun checkPin(pin: String): Result<UserModel> {
        val snapshot = db.collection("users")
            .whereEqualTo("pin", pin)
            .limit(1)
            .get().await()

        return if (snapshot.isEmpty) {
            Result.Empty
        } else {
            val data = snapshot.documents[0].toObject(UserModel::class.java)!!
            data.docId = snapshot.documents[0].id
            userPreference.saveSession(data)
            Result.Success(data)
        }
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            db: FirebaseFirestore,
            userPreference: UserPreference
        ): UserRepository = instance ?: synchronized(this) {
            instance ?: UserRepository(db, userPreference)
        }.also { instance = it }
    }
}