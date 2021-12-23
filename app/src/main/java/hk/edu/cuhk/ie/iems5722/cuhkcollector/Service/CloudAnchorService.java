package hk.edu.cuhk.ie.iems5722.cuhkcollector.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

import com.google.android.gms.maps.model.Marker;

import java.util.List;

import hk.edu.cuhk.ie.iems5722.cuhkcollector.ui.eventMap.EventMapFragment;

public class CloudAnchorService extends Service {

    public CloudAnchorService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    int TIME_INTERVAL = 5000; // 这是5s
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(TEST_ACTION);
        registerReceiver(receiver, intentFilter);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction(TEST_ACTION);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0低电量模式需要使用该方法触发定时任务
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上 需要使用该方法精确执行时间
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), pendingIntent);
        } else {//4。4一下 使用老方法
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), TIME_INTERVAL, pendingIntent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public static final String TEST_ACTION = "XXX.XXX.XXX" + "_TEST_ACTION";

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TEST_ACTION.equals(action)) {
                testAnchorDistance();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TIME_INTERVAL, pendingIntent);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TIME_INTERVAL, pendingIntent);
                }
            }
        }
    };

    public Location farLocation = new Location("farLocation");
    public Location nearLocation = new Location("nearLocation");

    private void testAnchorDistance(){
        farLocation.setLatitude(22.4181151);
        farLocation.setLongitude(114.207305);
        nearLocation.setLatitude(22.417871714353005);
        nearLocation.setLongitude(114.20731210099227);

        Location currLocation = EventMapFragment.lastKnownLocation;

        //以米为单位
        System.out.println("The far distance: " + currLocation.distanceTo(farLocation) + "\n");//27.26321
        System.out.println("The near distance: " + currLocation.distanceTo(nearLocation) + "\n");//0.7664323

    }

}