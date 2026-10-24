package com.uhackmancpu.tinyfloatinglock;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

/**
 * The launcher has no intermediate screen: it opens Android's Accessibility
 * settings so the user can enable or disable the lock overlay.
 */
public final class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        } catch (ActivityNotFoundException ignored) {
            // Android normally provides this settings activity.
        }
        finish();
    }
}
