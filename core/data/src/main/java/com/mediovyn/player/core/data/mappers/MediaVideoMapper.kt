package com.mediovyn.player.core.data.mappers

import com.mediovyn.player.core.common.Utils
import com.mediovyn.player.core.database.entities.MediumStateEntity
import com.mediovyn.player.core.media.services.MediaVideo
import com.mediovyn.player.core.model.Video
import java.util.Date

internal fun MediaVideo.toVideo(mediaState: MediumStateEntity? = null) = Video(
    id = id,
    uriString = uri.toString(),
    duration = duration,
    height = height,
    width = width,
    path = path,
    size = size,
    nameWithExtension = title,
    parentPath = parentPath,
    dateModified = dateModified,
    formattedDuration = Utils.formatDurationMillis(duration),
    formattedFileSize = Utils.formatFileSize(size),
    playbackPosition = mediaState?.playbackPosition,
    lastPlayedAt = mediaState?.lastPlayedTime?.let { Date(it) },
)
