package edu.hillel.notificationdemo;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by yuriy on 28.08.16.
 */
public class MyIntentService extends IntentService {

    private Notification.Builder builder;
    private NotificationManager nm;

    public MyIntentService() {
        super("MyServiceThread");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            int counter = 0;
            dropNotification();
            while (counter < 100) {
                Thread.sleep(500);
                counter++;
                updateNotification(counter);

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void dropNotification() {

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new Notification.Builder(this);
        builder.setSmallIcon(android.R.drawable.ic_delete);
        builder.setContentTitle("Title");
        builder.setContentText("This is my notification");

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_download);
        builder.setLargeIcon(bm);

        Intent i = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 100, i, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);

        builder.setAutoCancel(true);

        updateNotification(0);
    }

    private void updateNotification(int progress) {
        builder.setProgress(100, progress, false);
        if (progress < 100) {
            builder.setContentText("Download: " + progress + "%");
        } else {
            builder.setContentText("Download complete!");
        }

        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(101, builder.build());
    }
}
