package com.mediovyn.player.core.data.mappers

import com.mediovyn.player.core.model.AudioStreamInfo
import com.mediovyn.player.core.model.SubtitleStreamInfo
import com.mediovyn.player.core.model.VideoStreamInfo
import io.github.riteshbonthalakoti.mediovynlib.mediainfo.AudioStream
import io.github.riteshbonthalakoti.mediovynlib.mediainfo.SubtitleStream
import io.github.riteshbonthalakoti.mediovynlib.mediainfo.VideoStream

internal fun VideoStream.toVideoStreamInfo() = VideoStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
    bitRate = bitRate,
    frameRate = frameRate,
    frameWidth = frameWidth,
    frameHeight = frameHeight,
)

internal fun AudioStream.toAudioStreamInfo() = AudioStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
    bitRate = bitRate,
    sampleFormat = sampleFormat,
    sampleRate = sampleRate,
    channels = channels,
    channelLayout = channelLayout,
)

internal fun SubtitleStream.toSubtitleStreamInfo() = SubtitleStreamInfo(
    index = index,
    title = title,
    codecName = codecName,
    language = language,
    disposition = disposition,
)
