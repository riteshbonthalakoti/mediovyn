package com.mediovyn.player.core.media.network

import com.mediovyn.player.core.model.NetworkConnection

fun interface NetworkClientFactory {
    fun create(connection: NetworkConnection): NetworkClient
}
