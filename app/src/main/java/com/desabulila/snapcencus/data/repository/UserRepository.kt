package com.desabulila.snapcencus.data.repository

import com.desabulila.snapcencus.data.Result
import com.desabulila.snapcencus.data.local.pref.UserModel
import com.desabulila.snapcencus.data.local.pref.UserPreference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import java.util.UUID

class UserRepository private constructor(
    private val db: FirebaseFirestore,
    private val userPreference: UserPreference
) {

    suspend fun postPin(pin: String): Result<UserModel> {
        return try {
            val snapshot = db.collection("users")
                .whereEqualTo("pin", pin)
                .limit(1)
                .get()
                .await()

            if (snapshot.isEmpty) {
                Result.Empty
            } else {
                val data = snapshot.documents[0].toObject(UserModel::class.java)!!
                data.docId = snapshot.documents[0].id
                Result.Success(data)
            }
        } catch (exc: Exception) {
            Result.Error(exc.message.toString())
        }
    }

    suspend fun getUsers(): Result<List<UserModel>> {
        return try {
            val snapshot = db.collection("users").get().await()
            val userList = snapshot.documents.mapNotNull { document ->
                val user = document.toObject(UserModel::class.java)
                user?.apply { docId = document.id }
            }
            Result.Success(userList)
        } catch (exc: Exception) {
            Result.Error(exc.message.toString())
        }
    }

    suspend fun addUser(data: UserModel): Result<UserModel> {
        return try {
            val uId = UUID.randomUUID().toString()
            val dataSend = hashMapOf(
                "uId" to uId,
                "pin" to data.pin,
                "role" to "user",
                "name" to data.name
            )
            val documentRef = db.collection("users").add(dataSend).await()
            data.docId = documentRef.id
            Result.Success(data)
        } catch (exc: Exception) {
            Result.Error(exc.message.toString())
        }
    }

    suspend fun updateUser(user: UserModel): Result<Unit> {
        return try {
            val dataSend = hashMapOf<String, Any>(
                "pin" to (user.pin ?: ""),
                "name" to (user.name ?: "")
            )
            db.collection("users")
                .document(user.docId ?: return Result.Error("Document ID is null"))
                .update(dataSend).await()
            Result.Success(Unit)
        } catch (exc: Exception) {
            Result.Error(exc.message.toString())
        }
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
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