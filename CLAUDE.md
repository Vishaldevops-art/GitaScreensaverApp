# Gita Screensaver – Android App

## Goal
An Android app that shows Bhagavad Gita shlokas as a phone screensaver,
plus a simple main app to browse shlokas and change settings.
Target: publish on Google Play Store.

## Developer context
- Owner: Vishal — Azure DevOps engineer, NEW to Android development.
- Explain every step first (what it does, why), then do it.
- Communicate in Hinglish (Hindi-English mix).
- Dev machine: Windows 11 Pro, 32 GB RAM.

## Tech stack (decided)
- Language: Kotlin
- UI: Jetpack Compose (Material 3)
- Build: Gradle (Kotlin DSL), Android Studio
- Min SDK: 26 (Android 8.0) | Target SDK: latest stable
- Data: bundled JSON in `app/src/main/assets/` (offline, no backend)
- Version control: Git

## Features (MVP)
1. Screensaver via `DreamService` (Daydream) – shows rotating shlokas when charging/docked
2. Main app: browse chapters & shlokas
3. Each shloka: Sanskrit (Devanagari) + transliteration + Hindi meaning + English meaning
4. Settings: language, rotation interval, font size, dark/light theme

## Later (not MVP)
- Home screen widget ("Aaj ka shloka")
- Live wallpaper
- Favourites / share shloka

## Working rules for Claude
- Small, testable steps. One feature at a time → build → test → git commit.
- Always run a Gradle build after changes and fix errors before reporting done.
- Don't add libraries without explaining why.
- Update `PROGRESS.md` at the end of every session.
- Shloka text must be accurate; use a public-domain/verified source and note it in README.

## Roadmap
See `PROGRESS.md` for current status.
