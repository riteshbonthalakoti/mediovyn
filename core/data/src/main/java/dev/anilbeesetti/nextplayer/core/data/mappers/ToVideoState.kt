package com.mediovyn.player.core.data.mappers

import com.mediovyn.player.core.data.models.VideoState
import com.mediovyn.player.core.database.converter.UriListConverter
import com.mediovyn.player.core.database.entities.MediumStateEntity

fun MediumStateEntity.toVideoState(): VideoState {
    return VideoState(
        path = uriString,
        position = playbackPosition.takeIf { it != 0L },
        audioTrackIndex = audioTrackIndex,
        subtitleTrackIndex = subtitleTrackIndex,
        playbackSpeed = playbackSpeed,
        externalSubs = UriListConverter.fromStringToList(externalSubs),
        externalAudio = UriListConverter.fromStringToList(externalAudio),
        videoScale = videoScale,
        subtitleDelayMilliseconds = subtitleDelayMilliseconds,
        subtitleSpeed = subtitleSpeed,
    )
}
