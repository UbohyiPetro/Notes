package com.example.notes.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkConnection @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val _connectionState: MutableStateFlow<Boolean> = MutableStateFlow(true)

    init {
        val connection =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connection.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                _connectionState.value = true
            }

            override fun onLost(network: Network) {
                _connectionState.value = false
            }
        })
    }

    fun observeInternetConnection(): Flow<Boolean> {
        return _connectionState
    }

}