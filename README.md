# Tiny Floating Lock

A lightweight Android utility that adds a draggable floating screen-lock button using an Android Accessibility Service.

## Current features

- One tap invokes Android's native `GLOBAL_ACTION_LOCK_SCREEN` action (Android 9 / API 28+).
- Double tap opens Android's Power Menu.
- Long press opens quick controls for button appearance and media volume.
- Drag the floating button to reposition it.
- Resize the button using its resize handle or two-finger pinch.
- Adjust button size and opacity; preferences are saved locally on the device.
- Uses `TYPE_ACCESSIBILITY_OVERLAY`; no “Display over other apps” permission is required.
- Android continues to manage fingerprint, face unlock and device credentials.
- Accessibility service is configured not to retrieve other apps' window content.
- No advertising SDK or analytics SDK is included in the current version; no Internet permission is requested.
- No account or cloud service is required for app functionality.

## Install and enable

1. Install the APK on an Android 9 (API 28) or newer device.
2. Open Tiny Floating Lock.
3. Read and accept the in-app Accessibility disclosure.
4. In Android Accessibility settings, manually enable Tiny Floating Lock's service.
5. Return to an app and tap the floating lock button to lock the screen.

You can disable the Accessibility service at any time in Android Accessibility settings. Android manages the device's lock credentials and biometric authentication.

## Build status

- Android APK build workflow has completed successfully on the `feature/in-app-appearance-controls` branch.
- A Play Store Android App Bundle (AAB) build has also completed successfully, but that workflow artifact is **unsigned** and must be signed with this app's separate upload key before Play Console upload.
- Application ID: `com.dev.tinyfloatinglock`. Java namespace: `com.uhackmancpu.tinyfloatinglock`.
- GitHub Actions: [view workflow runs](https://github.com/uhackman-cpu/tiny-floating-lock/actions).
- This project has **not yet been published on Google Play**. A Google Play Console developer account has not been created yet.
- AdMob is not included. Advertising may be considered later; if added, the app, privacy policy and Play disclosures must be updated before that version is released.

## Privacy policy

The current-version privacy policy is published at:

**https://uhackman-cpu.github.io/tiny-floating-lock/privacy-policy.html**

The current version describes local preference storage and Accessibility Service use. It states that the app does not include ads or analytics SDKs, does not request Internet permission, and does not send personal information to the developer or third parties.

## Play Store preparation

- Listing copy: [`docs/play-store-listing.md`](docs/play-store-listing.md)
- Release checklist: [`docs/play-store-checklist.md`](docs/play-store-checklist.md)
- Screenshot audit: [`docs/play-store-assets-audit.md`](docs/play-store-assets-audit.md)
- App icon SVG source: `store-assets/app-icon.svg` (512×512).
- Feature graphic SVG source: `store-assets/feature-graphic.svg` (1024×500).
- Screenshot archive: [10 recreated HD promotional images](https://github.com/uhackman-cpu/tiny-floating-lock/blob/main/Tiny_Floating_Lock_10_HD_Recreated_Images.zip), stored on `main`.
- The archive was checked: all 10 files are RGB PNGs at 1080×1920, approximately 1.5–2.1 MB each. They are **recreated mockups, not raw physical-device captures**. Verify them against the exact release build before submitting; in particular, capture or verify the real Accessibility Settings flow shown in image 09.
- The existing **Prepare Play Store Assets and Screenshots** workflow renders the 512×512 icon and 1024×500 feature graphic into upload-ready PNG artifacts.

## Technical notes

- Minimum Android version: Android 9 (API 28); target API is 36.
- Uses Android's native accessibility global actions, not legacy Device Admin.
- The service is configured with `canRetrieveWindowContent=false`; it does not inspect screen text, passwords, messages or other apps' window contents.
- The app stores preferences such as button position, size, opacity and volume-gesture setting locally on the device.
- No third-party libraries are bundled in the current app build.
- Memory and battery use depend on the device, Android version and time the overlay is active.

## Play Store preparation checklist

- [x] Publish the privacy policy over HTTPS.
- [x] Successfully build the Android APK.
- [x] Successfully build an unsigned Play Store AAB.
- [x] Audit the supplied screenshot archive: 10 PNGs, 1080×1920, RGB.
- [ ] Create a Google Play Console developer account.
- [ ] Create and securely store a separate signing/upload key for Tiny Floating Lock.
- [ ] Sign the AAB and verify the signed artifact.
- [ ] Render and review the app icon and feature graphic; confirm dimensions, color mode and size.
- [ ] Verify/re-capture promotional mockups against the signed release, particularly the Accessibility Settings image.
- [ ] Complete Play Console App content, Accessibility declaration, Data Safety and other required forms based on the actual release build.
- [ ] Upload and complete any testing requirements shown by Play Console for the developer account.
