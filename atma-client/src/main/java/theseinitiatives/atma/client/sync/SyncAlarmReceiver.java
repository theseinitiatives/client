package theseinitiatives.atma.client.sync;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.util.Log;

public class SyncAlarmReceiver extends BroadcastReceiver {
    private String TAG = SyncAlarmReceiver.class.getSimpleName();
    public static final String ACTION = "theseinitiatives.atma.client.sync.alarm";
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private SyncService syncService;

    final long ONE_CYCLE = 15*60*1000;

    public SyncAlarmReceiver(){

    }

    public SyncAlarmReceiver(Context context){
        syncService = new SyncService(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: action="+intent.getAction());
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            ComponentName receiver = new ComponentName(context, SyncAlarmReceiver.class);
            PackageManager pm = context.getPackageManager();

            pm.setComponentEnabledSetting(receiver,
                    PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

            setAlarm(context);
        }else if(intent.getAction().equals(ACTION)){
            Log.d(TAG, "onReceive: SYNC!!!!");
            if (syncService!=null){
                syncService.startSync();
            }else{
                syncService = new SyncService(context);
                syncService.startSync();
            }
        }
    }

    private void setAlarm(Context context){
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, SyncAlarmReceiver.class);
        intent.setAction(ACTION);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() + ONE_CYCLE,
                ONE_CYCLE, alarmIntent);
        Log.d(TAG, "setAlarm: Sync Alarm Started");
    }

    public void startAlarm(Context context){
        Log.d(TAG, "startAlarm: called");
        setAlarm(context);
    }
}
