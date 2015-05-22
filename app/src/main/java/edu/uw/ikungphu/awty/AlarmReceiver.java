package edu.uw.ikungphu.awty;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String number = intent.getStringExtra("number");
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
        Toast.makeText(context, "SMS sent!", Toast.LENGTH_SHORT).show();
        //Log.d("Receiver", "sms sent, " + number);
    }

}
