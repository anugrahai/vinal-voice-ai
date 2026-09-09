# Vinal Voice AI — Android App

A 100% accessible Android app for visually impaired users, with:
- AI chat (xAI Grok-compatible API)
- Voice input (Speech-to-Text)
- Voice output (TTS with adjustable rate/pitch)
- Haptic vibration feedback
- 40+ chat style templates
- 5 languages (Indonesian, English, Arabic, Spanish, Malay)
- Accessibility Service for fully-blind navigation

## Build APK

GitHub Actions is configured to build the APK automatically on every push to `main`.

### To get your APK:

1. Go to the **Actions** tab of this repo
2. Click the latest workflow run
3. Scroll to **Artifacts** at the bottom
4. Download **`app-debug`**
5. Unzip → you get `app-debug.apk`

### To install on your phone:

1. Transfer `app-debug.apk` to your Android phone
2. Enable **Install from Unknown Sources** (Settings → Security)
3. Tap the APK file → Install

### To enable Accessibility Service (for fully-blind mode):

1. Open Vinal Voice AI
2. Tap the menu (⋮) → **Aktifkan Accessibility Service**
3. Find Vinal Voice AI in the list → toggle ON
4. Confirm permissions

## First-time setup:

1. Open app → **Setelan API & Otak AI**
2. Enter your xAI (or compatible) API key
3. Save
4. Go to **Chat Interaktif AI** → start chatting (voice or text)

## Project Structure

- `app/src/main/java/com/vinalvoiceai/app/`
  - `VinalApp.java` — Application class
  - `ui/MainActivity.java` — Main menu
  - `ui/chat/` — AI chat screen
  - `ui/gaya/` — Style picker (40+ templates)
  - `ui/audio/` — Audio & vibration settings
  - `ui/bahasa/` — Language switcher
  - `ui/settings/` — API key & model config
  - `service/VoiceAccessibilityService.java` — TalkBack-style service
  - `util/` — LangManager, TtsManager, VibrationManager, AiApiClient, GayaManager
  - `data/AppDatabase.java` — SharedPreferences wrapper
- `app/src/main/res/` — Layouts, strings (5 langs), themes, icons

## Build locally (optional)

```bash
./gradlew assembleDebug
# APK will be at: app/build/outputs/apk/debug/app-debug.apk
```

Requires JDK 17.
