package dev.shobhik.basicandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    TextView statusOfSpaceTravel;
    Button buttonFirst;
    Button buttonSecond;

    ServiceStatusReceiver serviceStatusReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        //Assign views
        buttonFirst = (Button) findViewById(R.id.button_first);
        buttonSecond = (Button) findViewById(R.id.button_second);
        statusOfSpaceTravel = (TextView) findViewById(R.id.status_of_planet);

        assignButtonActions();

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(Constants.BROADCAST_ACTION);
        serviceStatusReceiver = new ServiceStatusReceiver();
        LocalBroadcastManager.getInstance(mContext).registerReceiver(serviceStatusReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(serviceStatusReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void assignButtonActions() {

        buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceStarterIntent = new Intent(mContext, MyIntentService.class);
                serviceStarterIntent.setAction(Constants.BROADCAST_START_MISSION);
                startService(serviceStarterIntent);
            }
        });

        buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSomewhereElseIntent = new Intent(mContext, SecondActivity.class);
                startActivity(goSomewhereElseIntent);

            }
        });

    }



    private class ServiceStatusReceiver extends BroadcastReceiver {

        private ServiceStatusReceiver(){}

        @Override
        public void onReceive(Context context, Intent intent) {
            String status = intent.getStringExtra(Constants.BROADCAST_STATUS);
            statusOfSpaceTravel.setText(status);
        }
    }
}
