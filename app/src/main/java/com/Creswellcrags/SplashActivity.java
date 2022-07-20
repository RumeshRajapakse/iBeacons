package com.Creswellcrags;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import io.github.skyhacker2.sqliteonweb.SQLiteOnWeb;

public class SplashActivity extends AppCompatActivity {
    public int SPLASH_TIMEOUT = 3000;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Utils.addAllData();
        SQLiteOnWeb.init(this).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!checkLocationTurnon()) {
            openlocationsetting();
        }else {
            handler.postDelayed(splashRun, SPLASH_TIMEOUT);
        }

    }

    private boolean checkLocationTurnon() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            return false;
        } else {
            return true;
        }
    }

    public void openlocationsetting() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.gps_network_not_enabled)
                .setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 369);
                    }
                }).show();
    }


    private Runnable splashRun = new Runnable() {
        public void run() {
            startActivity(new Intent(SplashActivity.this, MainActivity1.class).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));
            finish();
        }
    };
}
