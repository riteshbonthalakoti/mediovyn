package com.mediovyn.player.core.media.network

import com.mediovyn.player.core.media.network.clients.FtpClient
import com.mediovyn.player.core.media.network.clients.SftpClient
import com.mediovyn.player.core.media.network.clients.SmbClient
import com.mediovyn.player.core.media.network.clients.WebDavClient
import com.mediovyn.player.core.media.network.keys.SshKeyStore
import com.mediovyn.player.core.model.NetworkConnection
import com.mediovyn.player.core.model.NetworkProtocol
import org.koin.core.annotation.Single

@Single
class DefaultNetworkClientFactory(
    private val sshKeyStore: SshKeyStore,
) : NetworkClientFactory {
    override fun create(connection: NetworkConnection): NetworkClient = when (connection.protocol) {
        NetworkProtocol.SMB -> SmbClient(connection)
        NetworkProtocol.FTP -> FtpClient(connection)
        NetworkProtocol.SFTP -> SftpClient(connection, sshKeyStore)
        NetworkProtocol.WEBDAV -> WebDavClient(connection)
    }
}
