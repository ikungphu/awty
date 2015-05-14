package edu.uw.ikungphu.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText editText;
    private EditText phone;
    private EditText frequency;
    private Button button;
    private Boolean alarmOn; // if alarm is on or not

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Are We There Yet?");

        editText = (EditText) findViewById(R.id.editText);
        phone = (EditText) findViewById(R.id.phone);
        frequency = (EditText) findViewById(R.id.frequency);
        button = (Button) findViewById(R.id.start);

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        alarmOn = (PendingIntent.getBroadcast(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmOn) {
            button.setText("Stop");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString().trim();
                String number = phone.getText().toString();
                String intervalString = frequency.getText().toString();
                if (!alarmOn) {
                    // message not empty, phone number 10 digits, time not empty
                    if (message.length() > 0 && number.length() == 10 && intervalString.length() > 0) {
                        // set alarm
                        int interval = Integer.parseInt(intervalString);

                        String phoneString = "(" + number.substring(0, 3) + ") " +
                                number.substring(3, 6) + "-" + number.substring(6);

                        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                        intent.putExtra("message", phoneString + ": " + message);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                        start(interval, pendingIntent);
                        button.setText("Stop");
                        alarmOn = true;
                        Toast.makeText(getApplicationContext(), "Alarm is set!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // stop service
                    Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                            0, intent, PendingIntent.FLAG_NO_CREATE);
                    stop(pendingIntent);
                    button.setText("Start");
                    alarmOn = false;
                    Toast.makeText(getApplicationContext(), "Alarm is cancelled!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void start(int interval, PendingIntent pendingIntent) {
        int time = interval * 1000;

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, time, pendingIntent);
    }

    private void stop(PendingIntent pendingIntent) {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}