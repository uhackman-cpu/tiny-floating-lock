# Tiny Floating Lock — Project Handover
**Updated:** 26 October 2026  
**Repository:** https://github.com/uhackman-cpu/tiny-floating-lock  
**Working branch:** `feature/in-app-appearance-controls`  
**Rule:** Continue on this feature branch; do not merge to `main` without the owner's approval.

## Goal
Prepare Tiny Floating Lock for Google Play release. The app provides a floating lock button and related controls. Keep changes scoped to this app; do not reuse the Invisible Keyboard app's signing key.

## Completed
- Added in-app appearance controls and removed the resize handle; Android APK build passed.
- Added Play Store listing draft: `docs/play-store-listing.md`.
- Added Play Store checklist: `docs/play-store-checklist.md`.
- Added SVG source artwork: `store-assets/app-icon.svg` and `store-assets/feature-graphic.svg`.
- Added workflow to render and upload the icon (512×512 PNG), feature graphic (1024×500 PNG), and listing draft.
- Graphics job succeeded in workflow run [40221061659](https://github.com/uhackman-cpu/tiny-floating-lock/actions/runs/40221061659).
- Privacy policy HTML exists at `site/privacy-policy.html`; deployment workflow exists at `.github/workflows/deploy-privacy-policy.yml`.
- Debug APK build passed in run [40221059699](https://github.com/uhackman-cpu/tiny-floating-lock/actions/runs/40221059699).
- AAB build passed in run [40221053885](https://github.com/uhackman-cpu/tiny-floating-lock/actions/runs/40221053885), but it is **unsigned** and not ready to upload to Play Console.
- Latest workflow file change commit: [7f88dc3](https://github.com/uhackman-cpu/tiny-floating-lock/commit/7f88dc3145b1c585eb1da46cb924b1765908ff01).

## Current blockers / verified status
- **Play Store screenshots are not ready.** Emulator screenshot job failed in run [40221061659](https://github.com/uhackman-cpu/tiny-floating-lock/actions/runs/40221061659). The latest log shows the emulator did boot, but the screenshot script exited immediately at `set -euo pipefail` with `/usr/bin/sh: Illegal option -o pipefail`. The action executes this script with `sh`, not Bash. This is the immediate issue to fix; it is separate from the earlier slow emulator boot and ADB Broken pipe errors.
- The workflow's screenshot step has not produced/uploaded a screenshot artifact.
- The privacy-policy page is not confirmed publicly available over HTTPS. An earlier deploy failed because GitHub Pages was not enabled/configured.
- No confirmed signed release AAB yet.
- Play Console account setup, store listing entry, testing and submission remain outstanding.

## Next actions (in order)
1. Fix `.github/workflows/capture-play-store-screenshots.yml`: remove `set -o pipefail` from the emulator action's script (or explicitly invoke Bash in a supported way). Keep `set -eu` if compatible. Check shell syntax and avoid long waits where possible.
2. Trigger the workflow once on `feature/in-app-appearance-controls`; wait for completion. Inspect the run logs and confirm that `tiny-floating-lock-play-store-screenshots` artifact exists and contains non-empty PNGs. Do not claim success before verifying the artifact.
3. Review the screenshots visually. If overlay/accessibility interactions remain unreliable, capture authentic screenshots on the owner's Vivo phone with the app installed, or use another real Android device. Do not present HTML mockups as screenshots of the real app.
4. Enable GitHub Pages for this repository (Settings → Pages → Build and deployment → GitHub Actions), then dispatch **Deploy Tiny Floating Lock Privacy Policy**. Verify the published HTTPS URL actually opens before adding it to Play Console.
5. Configure a **separate Tiny Floating Lock upload keystore**. Do not reuse the Invisible Keyboard keystore. Add signing values as GitHub Actions repository secrets: `ANDROID_KEYSTORE_BASE64`, `ANDROID_KEYSTORE_PASSWORD`, `ANDROID_KEY_ALIAS`, `ANDROID_KEY_PASSWORD`. Never paste passwords or keystore contents into chat.
6. Build and verify a **signed release AAB**, retain a secure backup of the keystore, and confirm package ID/version details.
7. Create/finish the Google Play Console account; add the verified privacy-policy URL, store listing, icon, feature graphic, screenshots and required app disclosures; complete the applicable testing requirements before submission.

## Latest relevant workflow runs
- [Prepare Play Store Assets and Screenshots — failed screenshot job, graphics succeeded](https://github.com/uhackman-cpu/tiny-floating-lock/actions/runs/40221061659)
- [Android APK Build — success](https://github.com/uhackman-cpu/tiny-floating-lock/actions/runs/40221059699)
- [Play Store AAB Build — unsigned AAB build succeeded](https://github.com/uhackman-cpu/tiny-floating-lock/actions/runs/40221053885)

## Release-readiness checklist
- [x] Debug APK builds
- [x] Store graphics generation workflow succeeds
- [x] Store listing draft and checklist committed
- [ ] Real screenshots generated and visually reviewed
- [ ] Privacy policy publicly accessible over HTTPS
- [ ] Separate signing keystore created and backed up securely
- [ ] Signed release AAB built and verified
- [ ] Play Console setup, disclosures and testing completed
- [ ] Final submission reviewed and approved by the owner

**Important:** Keep the project in the feature branch. Do not publish or merge to `main` without explicit approval.
