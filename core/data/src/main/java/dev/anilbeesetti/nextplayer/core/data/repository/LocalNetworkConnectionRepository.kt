package com.mediovyn.player.core.data.repository

import com.mediovyn.player.core.database.dao.NetworkConnectionDao
import com.mediovyn.player.core.database.entities.NetworkConnectionEntity
import com.mediovyn.player.core.model.NetworkAuthentication
import com.mediovyn.player.core.model.NetworkConnection
import com.mediovyn.player.core.model.NetworkProtocol
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Single

@Single
class LocalNetworkConnectionRepository(
    private val networkConnectionDao: NetworkConnectionDao,
) : NetworkConnectionRepository {

    override fun getConnections(): Flow<List<NetworkConnection>> =
        networkConnectionDao.getAll().map { entities -> entities.map { it.toModel() } }

    override suspend fun getConnection(id: Long): NetworkConnection? =
        networkConnectionDao.getById(id)?.toModel()

    override suspend fun upsert(connection: NetworkConnection): Long =
        networkConnectionDao.upsert(connection.toEntity())

    override suspend fun delete(id: Long) = networkConnectionDao.deleteById(id)

    private fun NetworkConnectionEntity.toModel() = NetworkConnection(
        id = id,
        name = name,
        protocol = runCatching { NetworkProtocol.valueOf(protocol) }.getOrDefault(NetworkProtocol.SMB),
        host = host,
        port = port,
        path = path,
        username = username,
        password = password,
        useHttps = useHttps,
        authentication = runCatching { NetworkAuthentication.valueOf(authentication) }
            .getOrDefault(NetworkAuthentication.PASSWORD),
        privateKeyFileName = privateKeyFileName,
        privateKeyPassphrase = privateKeyPassphrase,
        hostKeyFingerprint = hostKeyFingerprint,
    )

    private fun NetworkConnection.toEntity() = NetworkConnectionEntity(
        id = id,
        name = name,
        protocol = protocol.name,
        host = host,
        port = port,
        path = path,
        username = username,
        password = password,
        useHttps = useHttps,
        authentication = authentication.name,
        privateKeyFileName = privateKeyFileName,
        privateKeyPassphrase = privateKeyPassphrase,
        hostKeyFingerprint = hostKeyFingerprint,
    )
}
