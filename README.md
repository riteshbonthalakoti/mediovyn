# MEDIOVYN — Modern, High-Performance Native Android Video & Audio Player

[![Android CI Build](https://github.com/riteshbonthalakoti/mediovyn/actions/workflows/android_build.yaml/badge.svg)](https://github.com/riteshbonthalakoti/mediovyn/actions/workflows/android_build.yaml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-purple.svg)](https://kotlinlang.org)
[![AndroidX Media3](https://img.shields.io/badge/Media3-1.6.0-green.svg)](https://developer.android.com/guide/topics/media/media3)

**MEDIOVYN** is a native Android video and audio player platform built with **Kotlin**, **Jetpack Compose**, **AndroidX Media3 (ExoPlayer)**, and **Material 3 (Material You)**.

---

## ? Features

- **?? Hybrid Theme System:** OLED Deep Black (`#050508`), Electric Violet (`#7C3AED`), Cyan Glow (`#06B6D4`), and Glassmorphic translucent controls overlay, with support for **Material You Dynamic Color**.
- **??? 10-Band Equalizer & Volume Booster:** Native audio effect processing with 10 frequency bands, equalizer presets (Flat, Bass Boost, Vocal, Rock, Pop, Jazz), and software volume boost up to +15dB (~200%).
- **?? Cloud & Remote Media Streaming:** Direct stream playback for **HLS (`.m3u8`)**, **DASH (`.mpd`)**, **RTSP**, **SMB (Samba)**, **FTP/SFTP**, and **WebDAV** protocols with custom header injection.
- **?? Auto Subtitle Downloader:** OpenSubtitles REST API v3 hash-matching subtitle downloader with full font scaling, outline stroke, and timing delay sync offsets.
- **?? AES-256 Biometric Encrypted Vault:** Hidden media vault protected by Android `BiometricPrompt` (Fingerprint / Face Unlock) or fallback PIN with AES-256 GCM stream encryption.
- **?? Picture-in-Picture (PiP) & Background Playback:** Seamless floating window playback and audio background service.
- **?? Intuitive Gesture Controls:** Vertical brightness/volume swipe gestures and horizontal double-tap seeking.
- **?? 7-Day All-Access Trial & One-Time Pro Upgrade:** 7-day all-access trial followed by a single one-time Pro license unlock.

---

## ??? Architecture & Tech Stack

- **Language:** 100% Kotlin
- **UI Framework:** Jetpack Compose + Navigation 3
- **Playback Engine:** AndroidX Media3 / ExoPlayer
- **Dependency Injection:** Koin
- **Local Database:** Room DB
- **Preferences:** DataStore Preferences
- **Image Loading:** Coil 3

---

## ?? Building & Setup

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/riteshbonthalakoti/mediovyn.git
   cd mediovyn
   ```

2. **Open in Android Studio:**
   Import the project into Android Studio Ladybug or newer with JDK 17+.

3. **Build & Run:**
   ```bash
   ./gradlew assembleDebug
   ```

---

## ?? License

This project is licensed under the Apache License 2.0 / GPL-3.0.  
Copyright © 2026 Ritesh Bonthalakoti. All rights reserved.