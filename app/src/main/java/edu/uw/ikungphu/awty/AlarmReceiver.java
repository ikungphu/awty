package edu.uw.ikungphu.awty;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    private String message = "Currently running...";

    @Override
    public void onReceive(Context context, Intent intent) {
        message = intent.getStringExtra("message");
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
