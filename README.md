# Alya AI Engine — Stage 1

Landscape, fullscreen Android app (Kotlin + Jetpack Compose + Media3 ExoPlayer).
Flow: **intro video → home (looping video background) → PLAY → play-intro video (audio) → game selector**.

## What's included

- `app/src/main/java/com/alya/aiengine/` — all Kotlin source
  - `MainActivity.kt` — fullscreen/edge-to-edge setup + screen navigation (Crossfade)
  - `ui/video/FullScreenVideo.kt` — ExoPlayer wrapper; always keeps aspect ratio and
    **center-crops** instead of stretching (`RESIZE_MODE_ZOOM`)
  - `ui/screens/` — `IntroScreen`, `HomeScreen`, `PlayIntroScreen`, `GameSelectorScreen`
  - `ui/components/` — reusable glass panel + neon gradient buttons
  - `ui/theme/` — neon color palette (cyan / magenta / violet / amber / lime) + type scale
  - `data/Game.kt` — dummy game list (PUBG, MLBB, CODM, Free Fire, Genshin) — add more
    games here later, no other file needs to change
- `app/src/main/res/raw/` — your 3 videos, renamed to valid Android resource names:
  - `intro_video.mp4` (was "Vidio intro saat mau buka apk booster ini.mp4")
  - `background_video.mp4` (was "background utama vertikal dibikin horizontal.mp4")
  - `play_intro_video.mp4` (was "Vidio saat play gamenya sebelum masuk ke halaman game.mp4")
- `build_alya.bat` — same build workflow you already use for ControlOS

## Audio behavior (as specified)

| Video | Muted? | Loop? |
|---|---|---|
| `intro_video` | Muted | No — plays once, then Home |
| `background_video` | Muted | Yes — loops forever on Home |
| `play_intro_video` | **Audio ON** | No — plays once, then Game Selector |

Because each screen's video player is created and released by Compose as you navigate
(`Crossfade` + `DisposableEffect` in `FullScreenVideo.kt`), the background player is always
`stop()`+`release()`-d before the play-intro player starts, so the two can never overlap.

## How to build (same as ControlOS)

1. Unzip this project, e.g. to `C:\AlyaAIEngine`.
2. Open a terminal and run:

```bat
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.5.11-hotspot
set ANDROID_HOME=C:\Users\E15\AppData\Local\Android\Sdk
cd C:\AlyaAIEngine
gradlew.bat assembleDebug
```

   Or just double-click `build_alya.bat` (edit the `cd` line first if you unzip somewhere
   other than `C:\AlyaAIEngine`).

3. The APK will be at `app\build\outputs\apk\debug\app-debug.apk`.

**First run:** `gradlew.bat` will download Gradle 8.7 itself the first time you run it
(needs internet), then cache it — every build after that is offline/fast, same as ControlOS.

## What's deliberately NOT in this build yet

Per your stage-1 scope: no overlay, FPS counter, RAM/CPU/GPU/temperature monitor, Shizuku,
root, or the real booster logic. The game selector cards are wired up to be tappable and
"selected," but pressing one doesn't launch anything real yet — that's stage 2.

## Where to customize

- **Branding text**: `HomeScreen.kt` — the "ALYA" / "AI ENGINE" `Text` composables.
- **Colors**: `ui/theme/Color.kt` — five neon accents are already defined and used
  throughout; change these to retheme the whole app in one place.
- **Game list**: `data/Game.kt` — `GameRepository.dummyGames`.
- **App name shown under the launcher icon**: `res/values/strings.xml`.
