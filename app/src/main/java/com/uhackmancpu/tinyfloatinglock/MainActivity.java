package com.uhackmancpu.tinyfloatinglock;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

/** Opens Accessibility settings directly, without an intermediate app screen. */
public final class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openAccessibilitySettings();
        finish();
    }

    private void openAccessibilitySettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Intent detailIntent = new Intent(Settings.ACTION_ACCESSIBILITY_DETAILS_SETTINGS);
            detailIntent.putExtra(
                    Settings.EXTRA_ACCESSIBILITY_SERVICE_COMPONENT_NAME,
                    new ComponentName(this, LockAccessibilityService.class).flattenToString()
            );
            detailIntent.setData(Uri.parse("package:" + getPackageName()));
            try {
                startActivity(detailIntent);
                return;
            } catch (ActivityNotFoundException | SecurityException ignored) {
                // Some Android skins do not expose the service-specific settings page.
            }
        }
        try {
            startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        } catch (ActivityNotFoundException ignored) {
            // Android normally provides this settings activity.
        }
    }
}
