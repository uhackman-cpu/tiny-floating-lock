# Tiny Floating Lock

A dependency-free Android utility that adds a floating screen-lock button using an Android Accessibility Service.

## Features
- One tap invokes Android's native GLOBAL_ACTION_LOCK_SCREEN action (Android 9 / API 28+).
- Uses TYPE_ACCESSIBILITY_OVERLAY; no "Display over other apps" permission.
- Android still manages fingerprint, face unlock and device credentials.
- Drag the button body to move it.
- Drag the bottom-right handle to resize from 80 dp to 400 dp.
- Pinch with two fingers on the button to resize.
- Long-press the button for the Opacity and Size sliders.
- Saves size, opacity and position locally.
- No third-party libraries, internet permission, ads or persistent notification.
- Tapping the app icon opens Android Accessibility settings.

## Enable
1. Install the APK.
2. Open Tiny Floating Lock.
3. In Accessibility settings, select Tiny Floating Lock and enable the service.
4. Return to any app. Tap the floating lock to lock the screen.

To adjust transparency, long-press the floating button and move the Opacity slider. Lower opacity makes the button more transparent. Tap Done to close the panel.

## Build and download
GitHub Actions builds a debug APK on pushes to main and manual workflow runs. Download app-debug.apk from the workflow Artifacts section.

The APK is not guaranteed to be exactly 7.5 KB: Android packages contain manifests and resources. This app bundles no third-party libraries and requests no internet access. An active accessibility overlay uses some system memory; actual RAM and battery impact depend on the device and Android version.

## Technical notes
- Minimum Android version: Android 9 (API 28).
- Uses the native accessibility global action, not legacy Device Admin.
- Does not retrieve screen text or window contents.
