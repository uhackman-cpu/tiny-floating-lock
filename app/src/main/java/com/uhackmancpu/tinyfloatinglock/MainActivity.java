package com.uhackmancpu.tinyfloatinglock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Switch;

public final class MainActivity extends Activity {
    private static final String PREFS = "tiny_floating_lock";
    private static final int MIN_SIZE = 80;
    private static final int MAX_SIZE = 400;
    private android.content.SharedPreferences prefs;
    private TextView preview;
    private TextView sizeValue;
    private TextView opacityValue;

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        prefs = getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        int size = prefs.getInt("size_px", 160);
        int opacity = prefs.getInt("opacity", 84);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(9, 15, 25));
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(22), dp(28), dp(22), dp(28));
        scroll.addView(page);
        page.addView(label("Tiny Floating Lock", 27, Color.WHITE, true));
        page.addView(label("Appearance settings", 15, 0xFF9DB0C5, false), top(6));

        LinearLayout previewBox = new LinearLayout(this);
        previewBox.setGravity(Gravity.CENTER);
        previewBox.setOrientation(LinearLayout.VERTICAL);
        previewBox.setPadding(dp(12), dp(20), dp(12), dp(20));
        previewBox.setBackground(roundRect(0xFF121F30, dp(20), 0xFF2C4058));
        preview = label("🔒", 48, Color.WHITE, true);
        preview.setGravity(Gravity.CENTER);
        previewBox.addView(preview, new LinearLayout.LayoutParams(dp(Math.min(size, 240)), dp(Math.min(size, 240))));
        page.addView(previewBox, top(22));

        sizeValue = label("Button size: " + size + " px", 16, Color.WHITE, true);
        page.addView(sizeValue, top(24));
        SeekBar sizeSeek = new SeekBar(this);
        sizeSeek.setMax(MAX_SIZE - MIN_SIZE);
        sizeSeek.setProgress(size - MIN_SIZE);
        page.addView(sizeSeek, top(6));
        sizeSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar bar, int p, boolean user) {
                if (!user) return;
                int value = MIN_SIZE + p;
                sizeValue.setText("Button size: " + value + " px");
                updatePreview(value, prefs.getInt("opacity", 84));
                prefs.edit().putInt("size_px", value).apply();
            }
            public void onStartTrackingTouch(SeekBar bar) {}
            public void onStopTrackingTouch(SeekBar bar) {}
        });
        page.addView(label("Small 80 px                                      Large 400 px", 12, 0xFF9DB0C5, false), top(2));

        opacityValue = label("Transparency: " + (100 - opacity) + "%  ·  Opacity: " + opacity + "%", 16, Color.WHITE, true);
        page.addView(opacityValue, top(24));
        SeekBar opacitySeek = new SeekBar(this);
        opacitySeek.setMax(90);
        opacitySeek.setProgress(opacity - 10);
        page.addView(opacitySeek, top(6));
        opacitySeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar bar, int p, boolean user) {
                if (!user) return;
                int value = 10 + p;
                opacityValue.setText("Transparency: " + (100 - value) + "%  ·  Opacity: " + value + "%");
                updatePreview(prefs.getInt("size_px", 160), value);
                prefs.edit().putInt("opacity", value).apply();
            }
            public void onStartTrackingTouch(SeekBar bar) {}
            public void onStopTrackingTouch(SeekBar bar) {}
        });

        page.addView(label("Gesture controls", 18, Color.WHITE, true), top(30));
        Switch volumeGestureSwitch = new Switch(this);
        volumeGestureSwitch.setText("Double-tap + drag volume control");
        volumeGestureSwitch.setTextColor(Color.WHITE);
        volumeGestureSwitch.setChecked(prefs.getBoolean("volume_drag_enabled", true));
        page.addView(volumeGestureSwitch, top(8));
        volumeGestureSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                prefs.edit().putBoolean("volume_drag_enabled", isChecked).apply());

        TextView gestureHint = label(
                "ON: After a double tap, drag up to increase media volume or down to decrease it. "
                        + "OFF: vertical dragging moves the floating button normally.",
                13, 0xFFB8C7D8, false);
        gestureHint.setLineSpacing(dp(3), 1f);
        page.addView(gestureHint, top(6));

        TextView accessibility = button("Enable / manage floating lock");
        page.addView(accessibility, top(28));
        accessibility.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));

        TextView note = label("Single tap: lock screen. Double tap: open Android Power Menu. Hold: open quick controls with media-volume, size and opacity sliders. Drag the lock to move it; use a two-finger pinch to resize it.", 14, 0xFFB8C7D8, false);
        note.setLineSpacing(dp(4), 1f);
        page.addView(note, top(18));
        setContentView(scroll);
        updatePreview(size, opacity);
    }

    private void updatePreview(int size, int opacity) {
        if (preview == null) return;
        int box = Math.max(80, Math.min(240, size));
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) preview.getLayoutParams();
        lp.width = dp(box); lp.height = dp(box); preview.setLayoutParams(lp);
        preview.setAlpha(opacity / 100f);
        preview.setBackground(circle(0xFF142030, Color.WHITE));
        preview.setTextSize(Math.max(22, Math.min(72, box * 0.38f)));
    }
    private TextView label(String text, int sp, int color, boolean bold) {
        TextView t = new TextView(this); t.setText(text); t.setTextSize(sp); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD); return t;
    }
    private TextView button(String text) {
        TextView t = label(text, 16, Color.WHITE, true); t.setGravity(Gravity.CENTER);
        t.setPadding(dp(14), dp(15), dp(14), dp(15)); t.setBackground(roundRect(0xFF087F8C, dp(14), 0xFF087F8C)); return t;
    }
    private GradientDrawable roundRect(int fill, int radius, int stroke) {
        GradientDrawable d = new GradientDrawable(); d.setColor(fill); d.setCornerRadius(radius); d.setStroke(dp(1), stroke); return d;
    }
    private GradientDrawable circle(int fill, int stroke) {
        GradientDrawable d = new GradientDrawable(); d.setShape(GradientDrawable.OVAL); d.setColor(fill); d.setStroke(dp(2), stroke); return d;
    }
    private LinearLayout.LayoutParams top(int margin) {
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2); p.topMargin = dp(margin); return p;
    }
    private int dp(float v) { return Math.round(v * getResources().getDisplayMetrics().density); }
}
