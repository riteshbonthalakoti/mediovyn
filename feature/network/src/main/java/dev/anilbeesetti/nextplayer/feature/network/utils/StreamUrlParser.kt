package dev.anilbeesetti.nextplayer.feature.network.utils

import android.net.Uri

/**
 * Stream URL & Protocol Parser for MEDIOVYN.
 * Supports HLS (.m3u8), DASH (.mpd), RTSP, SMB, WebDAV, and custom HTTP header injection.
 */
enum class StreamProtocol {
    HLS,
    DASH,
    RTSP,
    PROGRESSIVE_HTTP,
    SMB,
    WEBDAV,
    FTP,
    UNKNOWN
}

data class StreamSourceInfo(
    val uri: Uri,
    val protocol: StreamProtocol,
    val headers: Map<String, String> = emptyMap(),
    val isLiveStream: Boolean = false
)

object StreamUrlParser {

    fun parseStreamUrl(rawUrl: String, customHeadersRaw: String? = null): StreamSourceInfo {
        val uri = Uri.parse(rawUrl.trim())
        val scheme = uri.scheme?.lowercase() ?: ""
        val path = uri.path?.lowercase() ?: ""

        val protocol = when {
            scheme == "rtsp" -> StreamProtocol.RTSP
            scheme == "smb" -> StreamProtocol.SMB
            scheme == "ftp" || scheme == "sftp" -> StreamProtocol.FTP
            path.endsWith(".m3u8") || rawUrl.contains("m3u8") -> StreamProtocol.HLS
            path.endsWith(".mpd") || rawUrl.contains("mpd") -> StreamProtocol.DASH
            scheme == "http" || scheme == "https" -> StreamProtocol.PROGRESSIVE_HTTP
            else -> StreamProtocol.UNKNOWN
        }

        val headers = mutableMapOf<String, String>()
        customHeadersRaw?.lines()?.forEach { line ->
            val parts = line.split(":", limit = 2)
            if (parts.size == 2) {
                headers[parts[0].trim()] = parts[1].trim()
            }
        }

        return StreamSourceInfo(
            uri = uri,
            protocol = protocol,
            headers = headers,
            isLiveStream = protocol == StreamProtocol.HLS || protocol == StreamProtocol.DASH || protocol == StreamProtocol.RTSP
        )
    }
}