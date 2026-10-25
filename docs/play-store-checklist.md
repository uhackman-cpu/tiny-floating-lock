# Tiny Floating Lock — Play Store release checklist

## Project status
- App name: Tiny Floating Lock
- Developer name: Dev Kashyap
- Support email: davidgamer7374@gmail.com
- Country / region: India
- Current package ID: `com.uhackmancpu.tinyfloatinglock`
- Minimum Android version: Android 9 (API 28)
- Target API: 36 (Android 16)
- Privacy policy draft: `docs/privacy-policy.html`

## Before creating a Play Console release
1. Create a Google Play Console developer account and complete Google's identity/contact verification. The account registration fee is paid directly to Google; never share login credentials or OTPs.
2. Decide and confirm the final package ID. Once published, changing the package ID means creating a different app listing.
3. Review the privacy policy and publish it at a publicly accessible, stable HTTPS URL. Do not submit a GitHub source URL as the policy URL. Enable GitHub Pages for this repository with the `docs/` folder as the publishing source, or host the policy on another site. The likely GitHub Pages path is `https://uhackman-cpu.github.io/tiny-floating-lock/privacy-policy.html` after Pages is enabled and the page is verified publicly.
4. Create a release upload keystore and keep a secure backup. Never commit the keystore or passwords to the repository. Configure GitHub Actions repository secrets:
   - `ANDROID_KEYSTORE_BASE64`: base64-encoded upload keystore file
   - `ANDROID_KEYSTORE_PASSWORD`
   - `ANDROID_KEY_ALIAS`
   - `ANDROID_KEY_PASSWORD`
5. Run the workflow `Signed Play Store AAB` manually after the four secrets are set. Download the `tiny-floating-lock-play-store-aab` artifact and validate it before upload.
6. Test the release build on physical devices: enable/disable accessibility service, single-tap lock, double-tap power menu/Lockdown handling, long-press controls, volume slider, double-tap vertical volume drag and its indicator, screen rotation, reboot, and service restart.
7. Complete Play Console store listing: short and full description, high-resolution app icon, feature graphic, phone screenshots, category, contact email, and content rating.
8. Complete App content declarations accurately: Data safety, ads declaration, target audience, content rating, privacy policy, and Accessibility API declaration where requested. This app's Accessibility Service is core functionality; explain it accurately and do not claim unrelated capabilities.
9. Because the developer account has not yet been created, check the account type and any current testing requirement in Play Console. New personal accounts created after 13 November 2023 generally must complete a closed test with at least 12 opted-in testers continuously for 14 days before applying for production access.
10. Upload the AAB to internal testing first. Fix any Play Console warnings, run closed testing if required, then apply for production access and submit for review.

## Important
- The privacy policy reflects the current no-ads/no-network implementation. If ads or analytics are added later, update the policy and Play Console declarations before shipping that version.
- The Play Store review decision is made by Google; a successful build does not guarantee approval.
- Never share Google account passwords, one-time codes, or keystore passwords in chat.
