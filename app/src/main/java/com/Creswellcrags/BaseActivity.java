package com.Creswellcrags;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.Creswellcrags.Model.BeaconModel;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

public class BaseActivity extends AppCompatActivity implements BeaconConsumer, RangeNotifier {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private static final int MY_BLUETOOTH_ENABLE_REQUEST_ID = 2;
    BeaconManager mBeaconManager;
    App app;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((App) this.getApplicationContext());
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, MY_BLUETOOTH_ENABLE_REQUEST_ID);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_BLUETOOTH_ENABLE_REQUEST_ID) {
            if (resultCode == RESULT_OK) {
                askpermission();
            }
            if (resultCode == RESULT_CANCELED) {
                // Request denied by user, or an error was encountered while
                // attempting to enable bluetooth
            }
        }

    }


    private void askpermission() {
        L.e("Ask permision");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Application needs location access");
                builder.setMessage("Please grant location access to detect beacons in the background.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                    @TargetApi(23)
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                PERMISSION_REQUEST_COARSE_LOCATION);
                        mBeaconManager = BeaconManager.getInstanceForApplication(getActivity());
                        mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
                        mBeaconManager.getBeaconParsers().add(new BeaconParser().
                                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
                        mBeaconManager.setBackgroundMode(false);
                        mBeaconManager.bind(getActivity());
                    }

                });
                builder.show();

            } else {
                mBeaconManager = BeaconManager.getInstanceForApplication(getActivity());
                mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
                mBeaconManager.getBeaconParsers().add(new BeaconParser().
                        setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
                mBeaconManager.setBackgroundMode(false);
                mBeaconManager.bind(getActivity());
            }

        } else {
            mBeaconManager = BeaconManager.getInstanceForApplication(getActivity());
            // Detect the URL frame:
            if (mBeaconManager != null && !mBeaconManager.isBound(this)) {
                mBeaconManager.getBeaconParsers().add(new BeaconParser().
                        setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
                mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
                mBeaconManager.bind(getActivity());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("@@@", "coarse location permission granted");
                    mBeaconManager = BeaconManager.getInstanceForApplication(getActivity());
                    mBeaconManager.getBeaconParsers().add(new BeaconParser().setBeaconLayout(BeaconParser.EDDYSTONE_URL_LAYOUT));
                    mBeaconManager.getBeaconParsers().add(new BeaconParser().
                            setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
                    mBeaconManager.setBackgroundMode(false);
                    mBeaconManager.bind(getActivity());

                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mBeaconManager.unbind(getActivity());
            }
        } else {
            mBeaconManager.unbind(getActivity());
        }
    }

    public void onBeaconServiceConnect() {
        Region region = new Region("all-beacons-region", null, null, null);
        try {
            mBeaconManager.startRangingBeaconsInRegion(region);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        mBeaconManager.setRangeNotifier(getActivity());
    }

    private BaseActivity getActivity() {
        return BaseActivity.this;
    }

    @Override
    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
        for (Beacon beacon : beacons) {
            if (beacon.getServiceUuid() == 0xFEAA) { // Eddystone frame uses a service Uuid of 0xfeaa

            } else { // AltBeacon or iBeacon frame
                BeaconModel mb = new BeaconModel();
                mb.uid = beacon.getId1() + "";
                BeaconModel bobj = Database.getdevice(mb);
                if (bobj != null) {
                    Database.updateDate(getActivity(),bobj);
                    if (Utils.isBeaconInList(mb.uid)) {
                        Database.createDevice(getActivity(), mb);
                    }
                } else {
                    mb = Utils.getBeaconDetails( mb.uid );
                    if (mb != null) {
                        if (Utils.isBeaconInList(mb.uid)) {
                            Database.createDevice(getActivity(), mb);
                        }
                    }
                }
            }


        }
    }


}
