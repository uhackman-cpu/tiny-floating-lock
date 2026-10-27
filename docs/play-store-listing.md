# Tiny Floating Lock — Google Play listing draft

## App name
Tiny Floating Lock

## Short description (under 80 characters)
Lock your screen with a movable floating button and quick controls.

## Full description
Keep screen locking close at hand with Tiny Floating Lock, a simple floating lock button for Android.

FEATURES
• Tap the floating button once to lock your screen.
• Double-tap to open Android's Power Menu.
• Press and hold to open quick controls for media volume, button size, and opacity.
• Move the button anywhere on the screen.
• Adjust the button's size and transparency.
• Use the optional double-tap-and-vertical-drag gesture to change media volume.
• Pinch with two fingers to resize the floating button.
• Choose the appearance settings that work best for your device.

ACCESSIBILITY SERVICE
Tiny Floating Lock uses Android's Accessibility Service to perform actions you trigger with the floating button: lock the screen, open Android's Power Menu, and show quick controls. The app displays an in-app disclosure and requests affirmative consent before opening Accessibility Settings. You must enable the service manually. The service is configured not to retrieve other apps' window content and does not inspect or transmit screen text, messages, passwords, or other apps' content. You can disable the service at any time in Android Settings.

PRIVACY
The current version contains no advertising SDK, analytics SDK, account system, or app network requests. It does not request Android's Internet permission. Preferences such as button position, size, opacity, and gesture settings stay on your device.

REQUIREMENTS
Android 9 or newer. System-menu behavior may vary by device manufacturer and Android version.

Support: davidgamer7374@gmail.com

## Suggested listing configuration
- App category: Tools
- Package / application ID: `com.dev.tinyfloatinglock` (confirmed in `app/build.gradle`; namespace is `com.uhackmancpu.tinyfloatinglock`)
- Ads: No for the current build. Update the app, privacy policy and Play Console declarations before releasing any build with ads.
- Contact email: davidgamer7374@gmail.com
- Privacy policy: https://uhackman-cpu.github.io/tiny-floating-lock/privacy-policy.html
- App icon source: `store-assets/app-icon.svg`; render to 512×512 32-bit PNG with alpha, at most 1 MB.
- Feature graphic source: `store-assets/feature-graphic.svg`; render to 1024×500 24-bit PNG or JPEG with no alpha.
- Screenshot source archive: [`Tiny_Floating_Lock_10_HD_Recreated_Images.zip`](https://github.com/uhackman-cpu/tiny-floating-lock/blob/main/Tiny_Floating_Lock_10_HD_Recreated_Images.zip) (on the `main` branch).

## Screenshot inventory and review

The supplied archive has 10 RGB PNG images, each 1080×1920 and around 1.5–2.1 MB. Their names and topics are:

1. `01_Tiny_Floating_Lock_HD.png` — hero / floating lock button.
2. `02_Single_Tap_Lock_Screen_HD.png` — one-tap screen lock.
3. `03_Double_Tap_Power_Menu_HD.png` — double-tap Power Menu.
4. `04_Double_Tap_Hold_Volume_Control_HD.png` — double-tap and vertical-drag volume gesture.
5. `05_Long_Press_Quick_Controls_HD.png` — quick controls.
6. `06_Drag_to_Move_HD.png` — moving the floating button.
7. `07_Two_Finger_Pinch_Resize_HD.png` — pinch-to-resize.
8. `08_Appearance_Settings_HD.png` — size and opacity settings.
9. `09_Accessibility_Setup_HD.png` — Accessibility settings setup illustration.
10. `10_Safe_and_Private_HD.png` — privacy claims.

Recommended initial review order: 1, 2, 5, 8, 3, 4, 6, 7, 9, 10. These are recreated promotional mockups, not untouched screenshots captured from a physical device. Before submission, compare every depicted action with the exact release AAB. In particular, replace or verify image 9 against the real Android Accessibility Settings screen on a test device; device settings differ between Android versions and manufacturers. Do not submit a visual that could imply an operating-system screen or app behavior that the release does not actually show.

Technical check: all 10 files meet Google Play's current screenshot pixel bounds (minimum 320 px, maximum 3840 px; no dimension exceeds twice the other). This check alone does not establish policy approval. See [official preview-asset requirements](https://support.google.com/googleplay/android-developer/answer/9866151?hl=en) and [metadata policy](https://support.google.com/googleplay/android-developer/answer/9898842?hl=en).

## Data safety / Accessibility declaration notes (verify against the actual release AAB)

- The current source has no Android `INTERNET` permission and no advertising or analytics SDKs.
- Preferences are stored locally; the app has no account, backend or network requests in the current build.
- The expected Data safety answer may be that no user data is collected or shared, but the developer must verify this against the exact release AAB, its manifest, and all bundled libraries before submitting the form. Google defines collection in terms of data transmitted off the device; see [Data safety guidance](https://support.google.com/googleplay/android-developer/answer/10787469?hl=en).
- Explain Accessibility API use accurately: user-triggered screen locking, Power Menu opening and floating controls. The service is configured with `canRetrieveWindowContent=false`.
- Complete the required Accessibility API declaration and provide a review video that shows the disclosure, consent/decline paths, enabling the service and core actions.
- Do not claim that Google has approved the app or that Accessibility API use guarantees approval.
