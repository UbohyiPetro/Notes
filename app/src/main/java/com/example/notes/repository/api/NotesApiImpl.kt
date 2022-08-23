package com.example.notes.repository.api

import com.example.notes.helper.NetworkConnection
import kotlinx.coroutines.*
import java.util.concurrent.CancellationException

class NotesApiImpl constructor(
    private val networkConnection: NetworkConnection
) : NotesApi {
    override suspend fun getAllNotes(): Result<Boolean> {
        val apiCall = CoroutineScope(Dispatchers.IO).async {
            makeCall()
        }
        CoroutineScope(Dispatchers.Default).launch {
            networkConnection.observeInternetConnection()
                .collect { isConnected ->
                    if (!isConnected) apiCall.cancel(CancellationException("No internet connection"))
                }
        }
        return try {
            val result = apiCall.await()
            Result.success(result)
        } catch (ex: CancellationException) {
            Result.failure(ex)
        }
    }

    private suspend fun makeCall(): Boolean {
        delay(5000)
        return true
    }
}