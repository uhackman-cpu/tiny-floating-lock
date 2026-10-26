# Tiny Floating Lock — Play Store release checklist

## Project status
- App name: Tiny Floating Lock
- Developer name: Dev Kashyap
- Support email: davidgamer7374@gmail.com
- Country / region: India
- Application ID: `com.dev.tinyfloatinglock` (from `app/build.gradle`)
- Java namespace / service package: `com.uhackmancpu.tinyfloatinglock`
- Minimum Android version: Android 9 (API 28)
- Target API: 36 (Android 16)
- Store listing draft: `docs/play-store-listing.md`
- Privacy policy source: `site/privacy-policy.html`
- Published privacy policy: https://uhackman-cpu.github.io/tiny-floating-lock/privacy-policy.html
- Google Play Console: developer account not created yet; no Play listing has been published.

## Store listing assets

### Supplied screenshot set — checked
- Archive: [`Tiny_Floating_Lock_10_HD_Recreated_Images.zip`](https://github.com/uhackman-cpu/tiny-floating-lock/blob/main/Tiny_Floating_Lock_10_HD_Recreated_Images.zip) on the repository's `main` branch.
- Inventory: 10 PNG files; all 1080×1920, RGB (no alpha); individual files are approximately 1.5–2.1 MB.
- Topics: product hero, one-tap lock, Power Menu, volume gesture, quick controls, drag, pinch resize, appearance settings, Accessibility setup, privacy.
- Visual review completed using a contact sheet. The set is polished and legible at reduced preview size, but these are **recreated promotional mockups**, not raw device captures.
- Before submission, check the visuals against the exact release AAB. Replace or verify `09_Accessibility_Setup_HD.png` on a real test device because Android settings screens vary by device/OS and the supplied image is an illustration.
- Suggested initial order to review: 01, 02, 05, 08, 03, 04, 06, 07, 09, 10. Only upload images that accurately represent the released behavior. Pixel dimensions meeting the technical bounds do not guarantee metadata policy compliance.
- Official requirements: [Play preview assets](https://support.google.com/googleplay/android-developer/answer/9866151?hl=en); [metadata policy](https://support.google.com/googleplay/android-developer/answer/9898842?hl=en).

### App icon and feature graphic
- Editable app icon source: `store-assets/app-icon.svg` (512×512 SVG).
- Editable feature graphic source: `store-assets/feature-graphic.svg` (1024×500 SVG).
- Workflow: `.github/workflows/capture-play-store-screenshots.yml`, manually run as **Prepare Play Store Assets and Screenshots**.
- It renders a 512×512 PNG icon and a 1024×500 PNG feature graphic, then uploads workflow artifacts. Before upload, ensure the icon is 32-bit PNG with alpha and at most 1 MB; the feature graphic must be JPEG or 24-bit PNG without alpha.

## Remaining steps before production release
1. Create the Google Play Console developer account and complete Google's identity/contact verification. Pay the registration fee directly to Google; never share login credentials or OTPs.
2. Confirm the application ID `com.dev.tinyfloatinglock` before creating the listing. Changing the application ID after publishing means creating a different app listing.
3. Privacy policy is published and has been checked with an HTTP 200 response. Recheck it in a signed-out/incognito browser before final submission.
4. Create an upload keystore on your own trusted device and keep at least one secure backup. Never commit the keystore or passwords. Add these repository Actions secrets:
   - `ANDROID_KEYSTORE_BASE64`: base64-encoded upload keystore file
   - `ANDROID_KEYSTORE_PASSWORD`
   - `ANDROID_KEY_ALIAS`
   - `ANDROID_KEY_PASSWORD`
5. Run **Signed Play Store AAB** manually after the four signing secrets are set. Download the `tiny-floating-lock-play-store-aab` artifact and verify its signature before uploading. Do not upload the unsigned AAB from the local-signing workflow.
6. Test the signed release on physical devices: enable/disable Accessibility service, single-tap lock, double-tap Power Menu, long-press controls, volume slider, double-tap vertical volume drag and indicator, pinch resize, screen rotation, reboot and service restart.
7. Finalize listing title, short/full description, icon, feature graphic, validated screenshots, category, support email and content rating.
8. Complete App content declarations accurately: Data safety, Ads declaration, target audience, content rating, privacy policy and Accessibility API declaration. The app shows an in-app Accessibility disclosure and asks for affirmative consent before opening system settings. Prepare the requested review video showing disclosure, consent/decline paths, enabling the service and core actions.
9. For Data safety, verify the exact release AAB's manifest and dependencies. Current source has no Android `INTERNET` permission, no ads/analytics SDKs and no network requests; preferences remain local. The expected answer may be no data collected/shared, but do not submit without checking the final artifact and its bundled SDKs. See [official Data safety guidance](https://support.google.com/googleplay/android-developer/answer/10787469?hl=en).
10. Check the account type and current testing requirement in Play Console. New personal developer accounts created after 13 November 2023 have generally been required to complete a closed test with at least 12 opted-in testers for 14 continuous days before applying for production access; verify the current requirement shown for this account.
11. Upload to internal testing first, resolve Play Console warnings, complete any required closed test, apply for production access and then submit for review.

## Important
- Current build does not include ads. If ads or analytics are added, update the policy and Play Console declarations before releasing that version.
- A successful build does not guarantee Google Play approval.
- Never share Google account passwords, OTPs or keystore passwords in chat.
