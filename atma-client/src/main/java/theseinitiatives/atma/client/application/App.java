package theseinitiatives.atma.client.application;

import android.app.Application;
import android.content.Context;

import com.flurry.android.FlurryAgent;

public class App extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        App.context = getApplicationContext();
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "2VXSK5VTHC2D7C3536RV");
    }

    public static Context getAppContext() {
        return App.context;
    }
}