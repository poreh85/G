
package com.glassblue.lite;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    LinearLayout root;
    TextView clock;
    android.os.Handler handler = new android.os.Handler();

    @Override
    public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().getDecorView().setSystemUiVisibility(5894);

        root = new LinearLayout(this);
        root.setOrientation(1);
        root.setGravity(Gravity.CENTER);
        root.setPadding(24, 16, 24, 16);
        root.setBackground(new GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            new int[]{0xff020817, 0xff103c75, 0xff031020}
        ));
        setContentView(root);

        clock = new TextView(this);
        clock.setTextColor(Color.WHITE);
        clock.setTextSize(54);
        clock.setTypeface(null, Typeface.BOLD);
        clock.setGravity(Gravity.CENTER);
        root.addView(clock);

        TextView title = new TextView(this);
        title.setText("GLASS BLUE LITE");
        title.setTextColor(0xff35d9ff);
        title.setTextSize(20);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        addRow(new String[]{"Maps", "Waze", "Music"});
        addRow(new String[]{"Radio", "Bluetooth", "Apps"});

        updateClock();
    }

    void addRow(String[] names) {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER);
        root.addView(row);

        for (String name : names) {
            Button btn = new Button(this);
            btn.setText(name);
            btn.setTextColor(Color.WHITE);
            btn.setTextSize(17);
            GradientDrawable bg = new GradientDrawable();
            bg.setColor(0x99305c91);
            bg.setCornerRadius(24);
            bg.setStroke(2, 0xff28d9ff);
            btn.setBackground(bg);

            LinearLayout.LayoutParams p =
                new LinearLayout.LayoutParams(180, 90);
            p.setMargins(10, 14, 10, 14);
            row.addView(btn, p);

            btn.setOnClickListener(v -> openApp(name));
        }
    }

    void openApp(String name) {
        String pkg = "";
        switch (name) {
            case "Maps": pkg = "com.google.android.apps.maps"; break;
            case "Waze": pkg = "com.waze"; break;
            case "Music": pkg = "com.google.android.apps.youtube.music"; break;
        }

        if (name.equals("Apps")) {
            Intent i = new Intent("android.settings.SETTINGS");
            try { startActivity(i); } catch (Exception e) {}
            return;
        }

        if (!pkg.isEmpty()) {
            Intent i = getPackageManager().getLaunchIntentForPackage(pkg);
            if (i != null) startActivity(i);
            else Toast.makeText(this, "Aplikasi tiada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                name + " belum dikonfigurasi",
                Toast.LENGTH_SHORT).show();
        }
    }

    void updateClock() {
        clock.setText(new SimpleDateFormat(
            "HH:mm", Locale.getDefault()).format(new Date()));
        handler.postDelayed(() -> updateClock(), 30000);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
