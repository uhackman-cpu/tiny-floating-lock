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
- GitHub Actions: [view workflow runs](https://github.com/uhackman-cpu/tiny-floating-lock/actions).
- This project has **not yet been published on Google Play**. A Google Play Console account has not been created yet.
- AdMob is not included. Advertising may be considered later; if added, the app, privacy policy and Play disclosures must be updated before release.

## Privacy policy

The current-version privacy policy is published at:

**https://uhackman-cpu.github.io/tiny-floating-lock/privacy-policy.html**

The current version describes local preference storage and Accessibility Service use. It states that the app does not include ads or analytics SDKs, does not request Internet permission, and does not send personal information to the developer or third parties.

## Technical notes

- Minimum Android version: Android 9 (API 28).
- Uses Android's native accessibility global actions, not legacy Device Admin.
- The service is configured with `canRetrieveWindowContent=false`; it does not inspect screen text, passwords, messages or other apps' window contents.
- The app stores preferences such as button position, size, opacity and volume-gesture setting locally on the device.
- No third-party libraries are bundled in the current app build.
- Memory and battery use depend on the device, Android version and time the overlay is active.

## Play Store preparation checklist

- [x] Publish the privacy policy over HTTPS.
- [x] Successfully build the Android APK.
- [x] Successfully build an unsigned Play Store AAB.
- [ ] Create a Google Play Console developer account.
- [ ] Create and securely store a separate signing/upload key for Tiny Floating Lock.
- [ ] Sign the AAB and verify the signed artifact.
- [ ] Finalize store listing, screenshots, feature graphic and app icon.
- [ ] Complete Play Console App content, Accessibility declaration, Data Safety and other required forms based on the actual release build.
- [ ] Upload and complete any testing requirements shown by Play Console for the developer account.

