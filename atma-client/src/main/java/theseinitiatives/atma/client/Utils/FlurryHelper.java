
package theseinitiatives.atma.client.Utils;

import com.flurry.android.FlurryAgent;

import java.util.Map;

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

}
