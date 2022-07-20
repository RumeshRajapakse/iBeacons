package com.Creswellcrags;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.util.Log;

import com.Creswellcrags.Model.BeaconModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rebecca McBath
 * on 2019-07-08.
 */
public class Utils {

    private static final String TAG = "Utils";


    public static boolean isBeaconInList(String udid) {

        if (udid.equalsIgnoreCase("07FD7DE9-CD00-F4C5-0CE5-B184676CF3FD")) {
            return true;
        } else if (udid.equalsIgnoreCase("CEC387FD-1AB6-D65A-A0BB-D64135FF9456")) {
            return true;
        } else if (udid.equalsIgnoreCase("E1338963-7436-0693-01EE-F71986B07B38")) {
            return true;
        } else if (udid.equalsIgnoreCase("C882B123-CED4-83CD-BD1B-72E339D76C52")) {
            return true;
        } else if (udid.equalsIgnoreCase("61C1F746-7E98-C6F7-0DB3-46B8013C8E03")) {
            return true;
        } else if (udid.equalsIgnoreCase("49F13DEE-361D-98DC-0BA0-692733B2EE83")) {
            return true;
        } else {
            return false;
        }

    }


    //-- Different AltBeaconModel Formats supported by AltBeacon
    public static final String LAYOUT_ALTBEACON = "m:2-3=beac,i:4-19,i:20-21,i:22-23,p:24-24,d:25-25";
    public static final String LAYOUT_EDDYSTONE_TLM = "x,s:0-1=feaa,m:2-2=20,d:3-3,d:4-5,d:6-7,d:8-11,d:12-15";
    public static final String LAYOUT_EDDYSTONE_UID = "s:0-1=feaa,m:2-2=00,p:3-3:-41,i:4-13,i:14-19";
    public static final String LAYOUT_EDDYSTONE_URL = "s:0-1=feaa,m:2-2=10,p:3-3:-41,i:4-20v";
    public static final String LAYOUT_IBEACON = "m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24";

    public boolean isBluetoothEnabled() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter.isEnabled();
    }

    public static ArrayList<BeaconModel> beaconstaticlist = new ArrayList<>();

    public static void addAllData() {

        beaconstaticlist.clear();

        BeaconModel bm = new BeaconModel();
        bm.uid = "07FD7DE9-CD00-F4C5-0CE5-B184676CF3FD";
        bm.Url = "creswell-crags.org.uk";
        bm.Title = "Creswell Crags 1";
        bm.desc = "Welcome to the Creswell Crags museum";
        bm.image = R.drawable.image1;
        beaconstaticlist.add(bm);

        bm = new BeaconModel();
        bm.uid = "CEC387FD-1AB6-D65A-A0BB-D64135FF9456";
        bm.Url = "";
        bm.Title = "Creswell Crags 2";
        bm.desc = "Cave Art at Creswell Crags";
        bm.image = R.drawable.image2;
        beaconstaticlist.add(bm);


        bm = new BeaconModel();
        bm.uid = "E1338963-7436-0693-01EE-F71986B07B38";
        bm.Url = "";
        bm.Title = "Creswell Crags 3";
        bm.desc = "Witch Marks";
        bm.image = R.drawable.image3;
        beaconstaticlist.add(bm);

        bm = new BeaconModel();
        bm.uid = "C882B123-CED4-83CD-BD1B-72E339D76C52";
        bm.Url = "";
        bm.Title = "Creswell Crags 4";
        bm.desc = "Cave Art at Creswell Crags";
        bm.image = R.drawable.image4;
        beaconstaticlist.add(bm);

        bm = new BeaconModel();
        bm.uid = "61C1F746-7E98-C6F7-0DB3-46B8013C8E03";
        bm.Url = "";
        bm.Title = "Creswell Crags 5";
        bm.desc = "Witch Marks";
        bm.image = R.drawable.image5;
        beaconstaticlist.add(bm);

        bm = new BeaconModel();
        bm.uid = "49F13DEE-361D-98DC-0BA0-692733B2EE83";
        bm.Url = "";
        bm.Title = "Creswell Crags 6";
        bm.desc = "Witch Marks";
        bm.image = R.drawable.image6;
        beaconstaticlist.add(bm);

    }


    public static BeaconModel getBeaconDetails(String uid) {
        BeaconModel bm = null;
        if (uid.equalsIgnoreCase("07FD7DE9-CD00-F4C5-0CE5-B184676CF3FD")) {
            bm = new BeaconModel();
            bm.uid = "07FD7DE9-CD00-F4C5-0CE5-B184676CF3FD";
            bm.Url = "creswell-crags.org.uk";
            bm.Title = "Creswell Crags 1";
            bm.desc = "Welcome to the Creswell Crags museum";
            bm.image = R.drawable.image1;
            return bm;
        } else if (uid.equalsIgnoreCase("CEC387FD-1AB6-D65A-A0BB-D64135FF9456")) {
            bm = new BeaconModel();
            bm.uid = "CEC387FD-1AB6-D65A-A0BB-D64135FF9456";
            bm.Url = "";
            bm.Title = "Creswell Crags 2";
            bm.desc = "Cave Art at Creswell Crags";
            bm.image = R.drawable.image2;
            return bm;
        } else if (uid.equalsIgnoreCase("E1338963-7436-0693-01EE-F71986B07B38")) {
            bm = new BeaconModel();
            bm.uid = "E1338963-7436-0693-01EE-F71986B07B38";
            bm.Url = "";
            bm.Title = "Creswell Crags 3";
            bm.desc = "Witch Marks";
            bm.image = R.drawable.image3;
            return bm;
        } else if (uid.equalsIgnoreCase("C882B123-CED4-83CD-BD1B-72E339D76C52")) {

            bm = new BeaconModel();
            bm.uid = "C882B123-CED4-83CD-BD1B-72E339D76C52";
            bm.Url = "";
            bm.Title = "Creswell Crags 4";
            bm.desc = "Cave Art at Creswell Crags";
            bm.image = R.drawable.image4;
            return bm;
        } else if (uid.equalsIgnoreCase("61C1F746-7E98-C6F7-0DB3-46B8013C8E03")) {
            bm = new BeaconModel();
            bm.uid = "61C1F746-7E98-C6F7-0DB3-46B8013C8E03";
            bm.Url = "";
            bm.Title = "Creswell Crags 5";
            bm.desc = "Witch Marks";
            bm.image = R.drawable.image5;
            return bm;
        } else if (uid.equalsIgnoreCase("49F13DEE-361D-98DC-0BA0-692733B2EE83")) {

            bm = new BeaconModel();
            bm.uid = "49F13DEE-361D-98DC-0BA0-692733B2EE83";
            bm.Url = "";
            bm.Title = "Creswell Crags 6";
            bm.desc = "Witch Marks";
            bm.image = R.drawable.image6;
            return bm;
        } else {
            return null;
        }

    }

    static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        final List<ActivityManager.RunningServiceInfo> services = activityManager.getRunningServices(Integer.MAX_VALUE);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : services) {
            Log.d(TAG, String.format("Service:%s", runningServiceInfo.service.getClassName()));
            if (runningServiceInfo.service.getClassName().equals(serviceClass.getName())) {
                return true;
            }
        }
        return false;
    }
}

