# Progress Tracker

Legend: [x] done · [~] in progress · [ ] pending

## Phase 0 – Setup
- [x] Project folder created (`Documents\GitaScreensaverApp`)
- [x] CLAUDE.md + PROGRESS.md + README.md added
- [x] Install Git (2.56.0, user.name/email configured)
- [x] Install Android Studio (includes JDK, Android SDK, emulator)
- [ ] Install Claude Code (native installer – Node.js NOT needed; optional but recommended)
- [x] `git init` + first commit
- [x] GitHub repo created + first push (https://github.com/Vishaldevops-art/GitaScreensaverApp)

## Phase 1 – Skeleton
- [x] Create empty Kotlin + Compose project in Android Studio (inside this folder)
- [x] Run "Hello World" on emulator
- [ ] Run on real phone (USB debugging)

## Phase 2 – Data
- [x] Shloka JSON schema finalised (`app/src/main/assets/shlokas.json`)
- [~] Sample set of 7 famous shlokas added (2.14, 2.20, 2.47, 4.7, 4.8, 6.5, 18.66) – verify against Gita Press
- [ ] Chapter 1–18 data added and verified

## Phase 3 – Main app UI
- [x] Home screen: random shloka card + "Next" + "Screensaver set karo" button
- [ ] Chapter list screen
- [ ] Shloka detail screen

## Phase 4 – Screensaver
- [x] DreamService implemented (`dream/GitaDreamService.kt`, plain Views, black background)
- [x] Shloka rotation (every 15 s) + fade animation + small position shift against burn-in
- [ ] Screensaver settings (interval, language, font size)

## Phase 5 – Settings & polish
- [ ] Language / font / theme settings
- [ ] App icon + splash

## Phase 6 – Release
- [ ] Release signing key (backup safely!)
- [ ] Privacy policy page
- [ ] Play Console account
- [ ] Closed testing with testers
- [ ] Production release

## Session log
- 2026-10-22: Project folder + planning docs created. Next: install Git & Android Studio.
- 2026-10-22: Phase 0 install guide shared. Decision: Claude Code via native installer (no Node.js). Next: install Git → Android Studio → Claude Code, then `git init` + first commit.
- 2026-10-22: Git 2.56.0 installed + global identity set. Next: Android Studio.
- 2026-10-24: Android Studio + SDK installed. `git init`, first commit, pushed to GitHub (`origin/main`). Next: Phase 1 – create empty Kotlin + Compose project.
- 2026-10-24: Empty Compose project created (package `com.vishaldevops.gitascreensaver`, minSdk 26, targetSdk 37, Gradle 9.6.0). Gradle download timed out from Java, so the zip was fetched manually and `networkTimeout` raised to 120000. App runs on emulator. Next: run on real phone, then Phase 2 (shloka data).
- 2026-10-24: Added 7 sample shlokas (JSON), home screen showing a shloka, and the Gita screensaver (DreamService). Tested on Pixel 8 emulator: screensaver starts and rotates shlokas. Emulator test needs: charging on, "stay awake while charging" off, light sensor > 0 (else Android shows its low-light clock instead). Next: test on real phone, then add all 700 shlokas.
