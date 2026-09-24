package com.mediovyn.player.feature.player.subtitles

import java.io.File
import java.io.RandomAccessFile
import java.nio.ByteBuffer
import java.nio.ByteOrder

/**
 * Computes OpenSubtitles 64-bit hash for exact video subtitle matching.
 */
object OpenSubtitlesHasher {

    fun computeHash(file: File): String {
        val size = file.length()
        val chunkSize = 64 * 1024L
        var hash = size

        RandomAccessFile(file, "r").use { raf ->
            val buffer = ByteArray(chunkSize.toInt())
            raf.readFully(buffer)
            hash += computeChunkHash(buffer)

            raf.seek((size - chunkSize).coerceAtLeast(0L))
            raf.readFully(buffer)
            hash += computeChunkHash(buffer)
        }

        return String.format("%016x", hash)
    }

    private fun computeChunkHash(buffer: ByteArray): Long {
        val bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN)
        var hash = 0L
        while (bb.hasRemaining()) {
            hash += bb.long
        }
        return hash
    }
}