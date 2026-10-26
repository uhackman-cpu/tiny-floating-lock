# Tiny Floating Lock — Play Store release checklist

## Project
- App name: Tiny Floating Lock
- Developer name: Dev Kashyap
- Support email: davidgamer7374@gmail.com
- Country / region: India
- Current package ID: `com.uhackmancpu.tinyfloatinglock`
- Minimum Android version: Android 9 (API 28)
- Target API: 36 (Android 16)
- Store listing draft: `docs/play-store-listing.md`
- Privacy policy source: `site/privacy-policy.html`

## Store assets
- Editable vector icon source: `store-assets/app-icon.svg`
- Editable vector feature graphic source: `store-assets/feature-graphic.svg`
- Run the manual workflow **Prepare Play Store Assets and Screenshots**. It creates two artifacts: `tiny-floating-lock-play-store-graphics` (512×512 icon, 1024×500 feature graphic, listing copy) and `tiny-floating-lock-play-store-screenshots` (emulator screenshots).
- Inspect every screenshot before submitting it; automated capture is not a substitute for a real-device review.

## Remaining steps before production release
1. Create the Google Play Console developer account and complete Google's identity/contact verification. Pay the registration fee directly to Google; never share login credentials or OTPs.
2. Confirm the final package ID. Changing it after publishing means creating a different app listing.
3. Publish the privacy policy at a stable, public HTTPS URL and verify it in an incognito browser. The workflow deploys the contents of `site/` to GitHub Pages.
   - Easiest route: repository Settings → Pages → Build and deployment → Source → **GitHub Actions**, then run **Deploy Tiny Floating Lock Privacy Policy**.
   - Alternative automation: add a fine-grained personal access token as repository Actions secret `PAGES_TOKEN`, granting repository administration and Pages write access; the workflow can then enable Pages automatically. Never paste this token into chat or commit it.
4. Create an upload keystore on your own trusted device and keep at least one secure backup. Never commit the keystore or passwords. Add these repository Actions secrets:
   - `ANDROID_KEYSTORE_BASE64`: base64-encoded upload keystore file
   - `ANDROID_KEYSTORE_PASSWORD`
   - `ANDROID_KEY_ALIAS`
   - `ANDROID_KEY_PASSWORD`
5. Run **Signed Play Store AAB** manually after the four signing secrets are set. Download the `tiny-floating-lock-play-store-aab` artifact and verify its signature before uploading. Do not upload the unsigned AAB from the local-signing workflow.
6. Test the signed release on physical devices: enable/disable accessibility service, single-tap lock, double-tap power menu/Lockdown handling, long-press controls, volume slider, double-tap vertical volume drag and indicator, screen rotation, reboot, and service restart.
7. Complete the listing with the short/full description, 512×512 app icon, 1024×500 feature graphic, phone screenshots, category, email, and content rating.
8. Complete App content declarations accurately: Data safety, ads declaration, target audience, content rating, privacy policy, and Accessibility API declaration. The app now shows an in-app Accessibility disclosure and asks for affirmative consent before opening system settings; provide the required declaration and a review video showing the disclosure, consent/decline paths, enabling the service, and core actions.
9. Check the account type and current testing requirement inside Play Console. New personal developer accounts created after 13 November 2023 have generally been required to complete a closed test with at least 12 opted-in testers for 14 continuous days before applying for production access; verify the current requirement for this account.
10. Upload to internal testing first. Resolve Play Console warnings, complete any required closed test, apply for production access, and then submit for review.

## Important
- The policy describes the current no-ads/no-network implementation. If ads or analytics are added, update the policy and Play Console declarations before releasing that version.
- A successful build does not guarantee Google Play approval.
- Never share Google account passwords, OTPs, or keystore passwords in chat.
