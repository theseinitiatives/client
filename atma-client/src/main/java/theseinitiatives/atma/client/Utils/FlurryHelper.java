
package theseinitiatives.atma.client.Utils;

import android.app.Activity;
import android.util.Log;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

import static theseinitiatives.atma.client.Utils.StringUtil.dateNow;

/**
 * Helps with logging custom events and errors to Flurry
 */
public class FlurryHelper {


    /**
     * Logs an event for analytics.
     *
     * @param eventName     name of the event
     * @param eventParams   event parameters (can be null)
     * @param timed         <code>true</code> if the event should be timed, false otherwise
     */
    public static void logEvent(String eventName, Map<String, String> eventParams, boolean timed) {
        FlurryAgent.logEvent(eventName, eventParams, timed);
    }

    /**
     * Ends a timed event that was previously started.
     *
     * @param eventName     name of the event
     * @param eventParams   event parameters (can be null)
     */
    public static void endTimedEvent(String eventName, Map<String, String> eventParams) {
        FlurryAgent.endTimedEvent(eventName, eventParams);
    }


    /**
     * Ends a timed event without event parameters.
     *
     * @param eventName     name of the event
     */
    public static void endTimedEvent(String eventName) {
        FlurryAgent.endTimedEvent(eventName);
    }

    /**
     * Logs an error.
     *
     * @param errorId           error ID
     * @param errorDescription  error description
     * @param throwable         a {@link Throwable} that describes the error
     */
    public static void logError(String errorId, String errorDescription, Throwable throwable) {
        FlurryAgent.onError(errorId, errorDescription, throwable);
    }

    /**
     * Logs location.
     *
     * @param latitude           latitude of location
     * @param longitude        longitude of location
     */
    public static void logLocation(double latitude, double longitude) {
        FlurryAgent.setLocation((float) latitude, (float) longitude);
    }

    /**
     * Logs page view counts.
     *
     */
    public static void logPageViews(){
        FlurryAgent.onPageView();
    }

    public static void logOneTimeEvent(String eventName, Map<String, String> eventParams){
        FlurryHelper.logEvent(eventName,eventParams,false);
    }

    public static void startFlurryLog(Activity activity){
        startFlurryLog(activity,"");
    }

    public static void endFlurryLog(Activity activity){
        endFlurryLog(activity,"");
    }

    public static void startFlurryLog(Activity activity, String additionalName){
        Log.d(activity.getClass().getSimpleName(), "startFlurryLog: "+activity.getClass().getSimpleName()+additionalName+" -> "+dateNow().toString());
        Map<String, String> Params = new HashMap<>();
        Params.put("Start",dateNow().toString());
        FlurryHelper.logEvent(activity.getClass().getSimpleName()+additionalName,Params,true);
    }

    public static void endFlurryLog(Activity activity, String additionalName){
        Log.d(activity.getClass().getSimpleName(), "endFlurryLog: "+activity.getClass().getSimpleName()+additionalName+" -> "+dateNow().toString());
        Map<String, String> Params = new HashMap<>();
        Params.put("End",dateNow().toString());
        FlurryHelper.endTimedEvent(activity.getClass().getSimpleName()+additionalName,Params);
    }

}
