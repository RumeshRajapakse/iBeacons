package com.Creswellcrags;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.Creswellcrags.Model.BeaconModel;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

public class MainActivity1 extends BaseActivity {
    ArrayList<BeaconModel> orderList = new ArrayList<>();
    BeaconAdapter beaconAdapter;
    SwipeMenuListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (SwipeMenuListView) findViewById(R.id.listview);
        beaconAdapter = new BeaconAdapter(MainActivity1.this, orderList);
        listview.setAdapter(beaconAdapter);
        updateview();
    }


    Handler handler = new Handler();
    Runnable getconnectedrunnable;

    private void updateview() {
        getconnectedrunnable = new Runnable() {
            @Override
            public void run() {
                setlistfromdb();
                handler.postDelayed(this, 5000);
            }
        };
        handler.post(getconnectedrunnable);
    }

    private void setlistfromdb() {

        ArrayList<BeaconModel> beconlist = Database.getDeviceList();
      //  if (orderList.size() < 1) {
            L.e("< 1 clear list");
            orderList.clear();
            orderList.addAll(beconlist);
            beaconAdapter.notifyDataSetChanged();
       /* }
        for (int i = 0; i < orderList.size(); i++) {
            if (Appconstant.getConnectedDiffinsec(orderList.get(i).Date, 15) == 2) {
                orderList.remove(i);
            }
        }

        for (int i = 0; i < beconlist.size(); i++) {
            for (int j = 0; j < orderList.size(); j++) {
                if(beconlist.get(i).uid.toLowerCase().equals(orderList.get(j).uid)){
                    break;
                }
                else {
                    orderList.add(beconlist.get(i));
                    break;
                }
            }
        }
*/
        beaconAdapter.notifyDataSetChanged();


    }


}
