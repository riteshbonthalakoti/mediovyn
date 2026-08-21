package com.mediovyn.player

import com.mediovyn.player.core.data.repository.NetworkConnectionRepository
import com.mediovyn.player.core.media.network.keys.SshKeyStore
import com.mediovyn.player.core.model.NetworkAuthentication
import com.mediovyn.player.core.model.NetworkProtocol
import kotlinx.coroutines.flow.first

internal suspend fun initializeSshKeyStore(
    repository: NetworkConnectionRepository,
    sshKeyStore: SshKeyStore,
) {
    val referencedFileNames = try {
        repository.getConnections().first()
            .asSequence()
            .filter { connection ->
                connection.protocol == NetworkProtocol.SFTP &&
                    connection.authentication == NetworkAuthentication.SSH_KEY
            }
            .mapNotNull { connection ->
                connection.privateKeyFileName.trim()
                    .takeIf(SshKeyStore::isValidFileName)
            }
            .toSet()
    } catch (_: Throwable) {
        null
    }
    sshKeyStore.initialize(referencedFileNames)
}
