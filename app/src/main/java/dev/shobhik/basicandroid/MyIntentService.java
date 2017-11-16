package dev.shobhik.basicandroid;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    private String[] planetNames = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune"};

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            if(intent.getAction() == Constants.BROADCAST_START_MISSION) {
                //Travel through planets
                for(int count = 0; count < 8; count ++) {
                    try {
                        Thread.sleep( 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String status  = "Hello " + planetNames[count];
                    Intent statusIntent = new Intent(Constants.BROADCAST_ACTION);
                    statusIntent.putExtra(Constants.BROADCAST_STATUS, status);
                    LocalBroadcastManager.getInstance(this).sendBroadcast(statusIntent);
                }

            } else if(intent.getAction() == Constants.BROADCAST_END_MISSION) {

                Intent statusIntent = new Intent(Constants.BROADCAST_ACTION);
                statusIntent.putExtra(Constants.BROADCAST_STATUS, "Mission aborted!");
                LocalBroadcastManager.getInstance(this).sendBroadcast(statusIntent);

            }

        }
    }


}
