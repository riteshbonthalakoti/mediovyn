package com.mediovyn.player.core.data.mappers

import com.mediovyn.player.core.media.services.MediaFolder
import com.mediovyn.player.core.model.Folder

internal fun MediaFolder.toFolder() = Folder(
    name = name,
    path = path,
    dateModified = dateModified,
    totalSize = totalSize,
    totalDuration = totalDuration,
    videosCount = videosCount,
    foldersCount = foldersCount,
)
