package com.Creswellcrags;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Appconstant {

    public static final String M_GET = "GET";
    public static final String M_POST = "POST";
    public static final String M_DELETE = "DELETE";



    public static boolean isNetworkAvailable(Context context) {
        try {

            ConnectivityManager nInfo = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            nInfo.getActiveNetworkInfo().isConnectedOrConnecting();

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

    public static int getConnectedDiffinsec(String lastdate, int diffsec) {
        if (lastdate != null) {
            if (lastdate.equals("")) {
                return 0; // send api
            } else {
                try {
                    if (lastdate.trim().equals("")) {
                        return 0; // send api
                    } else {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date dateObj = df.parse(lastdate);
                        Date c = Calendar.getInstance().getTime();
                        long diff = c.getTime() - dateObj.getTime();
                        long seconds = diff / 1000;
                      //20 >  10
                        if (seconds >= diffsec) {
                            return 2;// send api
                        } else {
                            return 1;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }

    public static int getConnectedDiff(String lastdate, int diffminute) {
        if (lastdate != null) {
            if (lastdate.equals("")) {
                return 0; // send api
            } else {
                try {
                    if (lastdate.trim().equals("")) {
                        return 0; // send api
                    } else {
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date dateObj = df.parse(lastdate);
                        Date c = Calendar.getInstance().getTime();
                        long diff = c.getTime() - dateObj.getTime();
                        long seconds = diff / 1000;
                        // 30 >  20
                        if (seconds >= diffminute) {
                            return 2;// send api
                        } else {
                            return 1;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        } else {
            return 0;
        }
    }
}
