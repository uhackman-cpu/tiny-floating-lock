# Tiny Floating Lock — Play Store asset audit

Audit date: 2026-10-26

## Screenshot archive checked

Source: [`Tiny_Floating_Lock_10_HD_Recreated_Images.zip`](https://github.com/uhackman-cpu/tiny-floating-lock/blob/main/Tiny_Floating_Lock_10_HD_Recreated_Images.zip) (stored on the repository's `main` branch).

The archive contains 10 PNG files. Each image was opened for metadata inspection and all 10 report:
- Dimensions: 1080×1920
- Mode: RGB (no alpha)
- Individual file size: approximately 1.5–2.1 MB
- PNG decode: successful for all 10 images

| # | File | Depicted content | Review note |
|---:|---|---|---|
| 1 | `01_Tiny_Floating_Lock_HD.png` | Hero / floating lock button | Good candidate for first image after behavior review |
| 2 | `02_Single_Tap_Lock_Screen_HD.png` | Single-tap screen lock | Verify against actual overlay and lock behavior |
| 3 | `03_Double_Tap_Power_Menu_HD.png` | Double-tap Power Menu | Android system menu and labels can vary by device |
| 4 | `04_Double_Tap_Hold_Volume_Control_HD.png` | Double-tap then vertical-drag media volume | Source code contains this gesture; test the signed release |
| 5 | `05_Long_Press_Quick_Controls_HD.png` | Media volume, size, opacity quick controls | Good candidate to show the main controls |
| 6 | `06_Drag_to_Move_HD.png` | Move floating button | Verify gestures match the release |
| 7 | `07_Two_Finger_Pinch_Resize_HD.png` | Two-finger resize gesture | Source code contains pinch handling; test the signed release |
| 8 | `08_Appearance_Settings_HD.png` | Size and opacity settings | Good candidate to show customization |
| 9 | `09_Accessibility_Setup_HD.png` | Android Accessibility settings | Recreated system UI. Replace with a real device capture or verify the screen/labels on the target device before submission |
| 10 | `10_Safe_and_Private_HD.png` | Privacy/security claims | Check every claim against the exact release AAB and privacy policy |

All images are **recreated promotional mockups**, not untouched screenshots captured directly from a physical device. They are legible in a contact-sheet review, but a visual design review cannot confirm functional accuracy. Google Play requires metadata and images to accurately represent actual functionality; do not submit a recreated system screen or an action depiction that differs materially from the real app.

Suggested initial review order: **1, 2, 5, 8, 3, 4, 6, 7, 9, 10**. This is a review order, not an automatic approval or an instruction to upload all 10. Prefer direct captures from the signed release for key in-app screens and system-settings flows.

## Other store assets

- App icon source: `store-assets/app-icon.svg`, 512×512.
- Feature graphic source: `store-assets/feature-graphic.svg`, 1024×500.
- `docs/play-store-listing.md` contains the current listing copy and Data safety / Accessibility notes.
- `docs/play-store-checklist.md` contains the remaining publishing steps.
- The existing workflow `Prepare Play Store Assets and Screenshots` renders upload-sized PNGs. It also has an emulator screenshot job; the supplied ZIP is separate and already inventoried above.

## Technical requirements checked against official guidance

- Play app icon: 512×512, 32-bit PNG with alpha, maximum 1 MB.
- Feature graphic: 1024×500, JPEG or 24-bit PNG without alpha.
- Screenshots: JPEG or 24-bit PNG without alpha; minimum dimension 320 px, maximum 3840 px; the longest dimension may not exceed twice the shortest dimension.
- The 10 supplied images meet the screenshot pixel bounds and have no alpha. Content-policy compliance still requires a separate behavior/accuracy review.

Official sources:
- [Add preview assets to showcase your app](https://support.google.com/googleplay/android-developer/answer/9866151?hl=en)
- [Metadata policy](https://support.google.com/googleplay/android-developer/answer/9898842?hl=en)
- [Data safety guidance](https://support.google.com/googleplay/android-developer/answer/10787469?hl=en)


## Rendered upload-pack validation

Generated and checked successfully by workflow [run #40358745695](https://github.com/uhackman-cpu/tiny-floating-lock/actions/runs/40358745695):

- `app-icon-512x512.png`: 512×512, sRGBA (32-bit with alpha), 52,993 bytes — below the 1 MB limit.
- `feature-graphic-1024x500.png`: 1024×500, sRGB (24-bit without alpha), 93,558 bytes.
- All ten included screenshots remain 1080×1920, sRGB (no alpha).
- Combined artifact: `tiny-floating-lock-play-store-upload-pack` (about 18.3 MB), containing the two rendered graphics, all ten screenshots and the listing/checklist/audit documents.

Review the included `READ-BEFORE-UPLOAD.txt` before submitting assets.
