package com.Creswellcrags;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;

import com.Creswellcrags.Model.BeaconModel;


public class NotificationGenrate extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intents) {

        L.e("Notification");

        if (intents.getExtras().containsKey("device")) {
            if (!PrefsUtil.with(context).readBoolean(PrefsUtil.isnotificationon)) {
                BeaconModel bm = (BeaconModel) intents.getSerializableExtra("device");
                L.e(bm.toString());
                if (!bm.Title.equals("")) {


                    NotificationManager mNotificationManager =
                            (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        NotificationChannel channel = new NotificationChannel("default",
                                context.getResources().getString(R.string.app_name),
                                NotificationManager.IMPORTANCE_HIGH);
                        channel.setDescription(bm.Url);
                        mNotificationManager.createNotificationChannel(channel);
                    }
                    NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle()
                            .bigPicture(BitmapFactory.decodeResource(context.getResources(),
                                    bm.image))
                            .setSummaryText(context.getResources().getString(R.string.app_name));


                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default")
                            .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                            .setContentTitle(bm.Title) // title for notification
                            .setContentText(bm.Url)// message for notification
                            .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                    bm.image))
                            .setStyle(style)
                            .setAutoCancel(true); // clear notification after click
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("url", bm.Url);
                    int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
                    PendingIntent pi = PendingIntent.getActivity(context, uniqueInt, intent, PendingIntent.FLAG_ONE_SHOT);
                    mBuilder.setContentIntent(pi);
                    mNotificationManager.notify(bm.uid.hashCode(), mBuilder.build());
                }
            }

        }

    }


}
