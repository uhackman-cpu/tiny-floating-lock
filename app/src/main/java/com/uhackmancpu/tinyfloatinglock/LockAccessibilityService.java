package com.uhackmancpu.tinyfloatinglock;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import android.content.SharedPreferences;

public final class LockAccessibilityService extends AccessibilityService implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String PREFS = "tiny_floating_lock";
    private static final int MIN_SIZE_PX = 80;
    private static final int MAX_SIZE_PX = 400;
    private static final int DEFAULT_SIZE_PX = 160;
    private static final int DEFAULT_OPACITY = 84;
    private static final long LONG_PRESS_MS = 620L;
    private static final long DOUBLE_TAP_MS = android.view.ViewConfiguration.getDoubleTapTimeout();

    private WindowManager windowManager;
    private WindowManager.LayoutParams overlayParams;
    private FrameLayout overlayRoot;
    private TextView lockButton;
    private View controlsPanel;
    private WindowManager.LayoutParams controlsParams;
    private SharedPreferences preferences;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private int sizeDp;
    private int sizePx;
    private int opacityPercent = DEFAULT_OPACITY;
    private float downRawX;
    private float downRawY;
    private int downWindowX;
    private int downWindowY;
    private int touchSlop;
    private boolean didMove;
    private boolean longPressTriggered;
    private boolean pinching;
    private long lastTapTime;
    private final Runnable pendingSingleTap = this::lockScreenNow;
    private float pinchStartDistance;
    private int pinchStartSizePx;
    private float resizeStartX;
    private float resizeStartY;
    private int resizeStartSizePx;

    private final Runnable longPressAction = () -> {
        longPressTriggered = true;
        showControls();
    };

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        preferences = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        preferences.registerOnSharedPreferenceChangeListener(this);
        touchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        sizePx = clamp(preferences.getInt("size_px", DEFAULT_SIZE_PX), MIN_SIZE_PX, MAX_SIZE_PX);
        sizeDp = pxToDp(sizePx);
        opacityPercent = clamp(preferences.getInt("opacity", DEFAULT_OPACITY), 15, 100);
        createFloatingButton();
    }

    private void createFloatingButton() {
        if (windowManager == null) return;
        overlayRoot = new FrameLayout(this);
        overlayRoot.setClipChildren(false);
        overlayRoot.setClipToPadding(false);

        lockButton = new TextView(this);
        lockButton.setText("🔒");
        lockButton.setGravity(Gravity.CENTER);
        lockButton.setIncludeFontPadding(false);
        lockButton.setTextColor(Color.WHITE);
        lockButton.setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD);
        lockButton.setContentDescription("Lock screen. Drag to move. Change size and transparency in the app settings.");
        lockButton.setBackground(circleDrawable(Color.rgb(20, 32, 48), Color.WHITE, dp(2)));
        lockButton.setAlpha(opacityPercent / 100f);
        FrameLayout.LayoutParams buttonParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
        );
        overlayRoot.addView(lockButton, buttonParams);
        lockButton.setOnTouchListener(this::handleButtonTouch);

        Point screen = screenSize();
        int initialX = preferences.getInt("x", Math.max(dp(8), screen.x - sizePx - dp(14)));
        int initialY = preferences.getInt("y", Math.max(dp(8), (screen.y - sizePx) / 2));
        overlayParams = new WindowManager.LayoutParams(
                sizePx,
                sizePx,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );
        overlayParams.gravity = Gravity.TOP | Gravity.START;
        overlayParams.x = initialX;
        overlayParams.y = initialY;
        overlayParams.setTitle("Tiny Floating Lock");
        clampOverlayPosition();
        updateButtonVisuals();
        try {
            windowManager.addView(overlayRoot, overlayParams);
        } catch (WindowManager.BadTokenException | IllegalStateException exception) {
            Toast.makeText(this, "Could not display the lock button. Re-enable the accessibility service.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean handleButtonTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downRawX = event.getRawX();
                downRawY = event.getRawY();
                downWindowX = overlayParams.x;
                downWindowY = overlayParams.y;
                didMove = false;
                longPressTriggered = false;
                pinching = false;
                handler.removeCallbacks(longPressAction);
                handler.postDelayed(longPressAction, LONG_PRESS_MS);
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() >= 2) {
                    handler.removeCallbacks(longPressAction);
                    pinching = true;
                    didMove = true;
                    pinchStartDistance = distanceBetweenFirstTwoPointers(event);
                    pinchStartSizePx = sizePx;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerCount() >= 2) {
                    if (!pinching) {
                        handler.removeCallbacks(longPressAction);
                        pinching = true;
                        didMove = true;
                        pinchStartDistance = distanceBetweenFirstTwoPointers(event);
                        pinchStartSizePx = sizePx;
                    }
                    int delta = Math.round(distanceBetweenFirstTwoPointers(event) - pinchStartDistance);
                    updateSizePx(pinchStartSizePx + delta);
                    return true;
                }
                if (!pinching) {
                    float dx = event.getRawX() - downRawX;
                    float dy = event.getRawY() - downRawY;
                    if (Math.abs(dx) > touchSlop || Math.abs(dy) > touchSlop) {
                        didMove = true;
                        handler.removeCallbacks(longPressAction);
                    }
                    if (didMove) {
                        overlayParams.x = downWindowX + Math.round(dx);
                        overlayParams.y = downWindowY + Math.round(dy);
                        clampOverlayPosition();
                        updateOverlay();
                    }
                }
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                return true;
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(longPressAction);
                if (!didMove && !longPressTriggered && !pinching) {
                    long now = android.os.SystemClock.elapsedRealtime();
                    if (lastTapTime > 0 && now - lastTapTime <= DOUBLE_TAP_MS) {
                        handler.removeCallbacks(pendingSingleTap);
                        lastTapTime = 0;
                        openHardLockMenu();
                    } else {
                        lastTapTime = now;
                        handler.postDelayed(pendingSingleTap, DOUBLE_TAP_MS);
                    }
                } else {
                    savePosition();
                    saveSize();
                }
                pinching = false;
                return true;
            case MotionEvent.ACTION_CANCEL:
                handler.removeCallbacks(longPressAction);
                pinching = false;
                savePosition();
                saveSize();
                return true;
            default:
                return true;
        }
    }

    private boolean handleResizeTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                resizeStartX = event.getRawX();
                resizeStartY = event.getRawY();
                resizeStartSizePx = sizePx;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getRawX() - resizeStartX;
                float dy = event.getRawY() - resizeStartY;
                updateSizePx(resizeStartSizePx + Math.round((dx + dy) / 2f));
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                saveSize();
                savePosition();
                return true;
            default:
                return true;
        }
    }


    private void openHardLockMenu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            // Android does not let ordinary apps silently disable biometrics.
            // Open the system power menu so the user can choose Lockdown, if enabled.
            boolean opened = performGlobalAction(GLOBAL_ACTION_POWER_DIALOG);
            if (opened) {
                Toast.makeText(this, "Choose Lockdown in the power menu to require PIN, pattern, or password. If Lockdown is not shown, enable it in your phone security settings.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Could not open the power menu. Use your phone's power menu and choose Lockdown.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Hard Lock requires Android 9 or newer. Use the power menu Lockdown option if available.", Toast.LENGTH_LONG).show();
        }
    }

    private void lockScreenNow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            boolean dispatched = performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN);
            if (!dispatched) {
                Toast.makeText(this, "Android could not lock the screen. Check accessibility access.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Screen locking requires Android 9 or newer.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSizePx(int requestedPx) {
        sizePx = clamp(requestedPx, MIN_SIZE_PX, MAX_SIZE_PX);
        sizeDp = pxToDp(sizePx);
        if (overlayParams != null) {
            overlayParams.width = sizePx;
            overlayParams.height = sizePx;
            clampOverlayPosition();
            updateOverlay();
        }
        updateButtonVisuals();
    }

    private void updateButtonVisuals() {
        if (lockButton != null) {
            lockButton.setAlpha(opacityPercent / 100f);
            lockButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, Math.max(16f, Math.min(86f, sizeDp * 0.58f)));
            lockButton.setBackground(circleDrawable(Color.rgb(20, 32, 48), Color.WHITE, Math.max(dp(1), dp(sizeDp / 80))));
        }
    }

    private void setOpacity(int percent) {
        opacityPercent = clamp(percent, 15, 100);
        updateButtonVisuals();
    }

    private void showControls() {
        if (windowManager == null || overlayRoot == null) return;
        dismissControls();

        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.VERTICAL);
        panel.setPadding(dp(16), dp(14), dp(16), dp(14));
        panel.setElevation(dp(8));
        panel.setBackground(roundRect(0xF7F6F8FA, dp(18), 0xFFE0E6ED));
        panel.addView(textLabel("Button appearance", 17, 0xFF102030, true), matchWrap());

        TextView opacityLabel = textLabel("", 14, 0xFF243447, true);
        panel.addView(opacityLabel, topMargin(matchWrap(), 6));
        SeekBar opacitySeek = new SeekBar(this);
        opacitySeek.setMax(100);
        opacitySeek.setProgress(opacityPercent);
        panel.addView(opacitySeek, matchWrap());
        opacityLabel.setText("Opacity: " + opacityPercent + "%");
        opacitySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) setOpacity(progress);
                opacityLabel.setText("Opacity: " + opacityPercent + "%");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                preferences.edit().putInt("opacity", opacityPercent).apply();
            }
        });

        TextView sizeLabel = textLabel("", 14, 0xFF243447, true);
        panel.addView(sizeLabel, topMargin(matchWrap(), 8));
        SeekBar sizeSeek = new SeekBar(this);
        sizeSeek.setMax(MAX_SIZE_PX - MIN_SIZE_PX);
        sizeSeek.setProgress(sizePx - MIN_SIZE_PX);
        panel.addView(sizeSeek, matchWrap());
        sizeLabel.setText("Button size: " + sizePx + " px");
        sizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) updateSizePx(MIN_SIZE_PX + progress);
                sizeLabel.setText("Button size: " + sizePx + " px");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override public void onStopTrackingTouch(SeekBar seekBar) {
                saveSize();
                savePosition();
            }
        });

        TextView done = textLabel("Done", 15, Color.WHITE, true);
        done.setGravity(Gravity.CENTER);
        done.setPadding(dp(12), dp(10), dp(12), dp(10));
        done.setBackground(roundRect(0xFF087F8C, dp(12), 0xFF087F8C));
        done.setOnClickListener(v -> dismissControls());
        panel.addView(done, topMargin(matchWrap(), 12));

        int panelWidth = dp(260);
        controlsParams = new WindowManager.LayoutParams(
                panelWidth,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );
        controlsParams.gravity = Gravity.TOP | Gravity.START;
        controlsParams.setTitle("Tiny Floating Lock appearance controls");
        Point screen = screenSize();
        int estimatedHeight = dp(240);
        int candidateY = overlayParams.y >= estimatedHeight + dp(8)
                ? overlayParams.y - estimatedHeight - dp(8)
                : overlayParams.y + sizePx + dp(8);
        controlsParams.x = clamp(overlayParams.x, dp(4), Math.max(dp(4), screen.x - panelWidth - dp(4)));
        controlsParams.y = clamp(candidateY, dp(4), Math.max(dp(4), screen.y - estimatedHeight - dp(4)));
        controlsPanel = panel;
        try {
            windowManager.addView(controlsPanel, controlsParams);
        } catch (WindowManager.BadTokenException | IllegalStateException exception) {
            controlsPanel = null;
            Toast.makeText(this, "Could not open appearance controls.", Toast.LENGTH_SHORT).show();
        }
    }

    private TextView textLabel(String text, int textSizeSp, int color, boolean bold) {
        TextView label = new TextView(this);
        label.setText(text);
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp);
        label.setTextColor(color);
        if (bold) label.setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD);
        return label;
    }

    private LinearLayout.LayoutParams matchWrap() {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
    }

    private LinearLayout.LayoutParams topMargin(LinearLayout.LayoutParams params, int marginDp) {
        params.topMargin = dp(marginDp);
        return params;
    }

    private void dismissControls() {
        if (windowManager != null && controlsPanel != null) {
            try {
                windowManager.removeView(controlsPanel);
            } catch (IllegalArgumentException ignored) {
                // The view may already have been detached by Android.
            }
        }
        controlsPanel = null;
        controlsParams = null;
    }

    private GradientDrawable circleDrawable(int fill, int stroke, int strokeWidth) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(fill);
        drawable.setStroke(Math.max(1, strokeWidth), stroke);
        return drawable;
    }

    private GradientDrawable roundRect(int fill, int radius, int stroke) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setColor(fill);
        drawable.setCornerRadius(radius);
        drawable.setStroke(dp(1), stroke);
        return drawable;
    }

    private float distanceBetweenFirstTwoPointers(MotionEvent event) {
        float dx = event.getX(0) - event.getX(1);
        float dy = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void clampOverlayPosition() {
        if (overlayParams == null) return;
        Point screen = screenSize();
        overlayParams.x = clamp(overlayParams.x, 0, Math.max(0, screen.x - overlayParams.width));
        overlayParams.y = clamp(overlayParams.y, 0, Math.max(0, screen.y - overlayParams.height));
    }

    private void updateOverlay() {
        if (windowManager != null && overlayRoot != null && overlayRoot.isAttachedToWindow()) {
            try {
                windowManager.updateViewLayout(overlayRoot, overlayParams);
            } catch (IllegalArgumentException | WindowManager.BadTokenException ignored) {
                // The service may be in the process of being disabled.
            }
        }
    }

    private void savePosition() {
        if (preferences != null && overlayParams != null) {
            preferences.edit().putInt("x", overlayParams.x).putInt("y", overlayParams.y).apply();
        }
    }

    private void saveSize() {
        if (preferences != null) preferences.edit().putInt("size_px", sizePx).apply();
    }

    private Point screenSize() {
        Point point = new Point();
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getRealSize(point);
        } else {
            point.x = getResources().getDisplayMetrics().widthPixels;
            point.y = getResources().getDisplayMetrics().heightPixels;
        }
        return point;
    }

    private int pxToDp(int valuePx) {
        return Math.round(valuePx / getResources().getDisplayMetrics().density);
    }

    private int dp(float value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                value,
                getResources().getDisplayMetrics()
        ));
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        if (prefs == null) return;
        if ("size_px".equals(key)) updateSizePx(prefs.getInt("size_px", DEFAULT_SIZE_PX));
        if ("opacity".equals(key)) {
            opacityPercent = clamp(prefs.getInt("opacity", DEFAULT_OPACITY), 10, 100);
            updateButtonVisuals();
        }
        if ("x".equals(key) || "y".equals(key)) {
            if (overlayParams != null) {
                overlayParams.x = prefs.getInt("x", overlayParams.x);
                overlayParams.y = prefs.getInt("y", overlayParams.y);
                clampOverlayPosition();
                updateOverlay();
            }
        }
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // No window text or content is needed for this workflow.
    }

    @Override
    public void onInterrupt() {
        // Android invokes this if the accessibility service is interrupted.
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(longPressAction);
        handler.removeCallbacks(pendingSingleTap);
        if (preferences != null) preferences.unregisterOnSharedPreferenceChangeListener(this);
        dismissControls();
        if (windowManager != null && overlayRoot != null) {
            try {
                windowManager.removeView(overlayRoot);
            } catch (IllegalArgumentException ignored) {
                // Already removed.
            }
        }
        overlayRoot = null;
        lockButton = null;
        super.onDestroy();
    }
}
