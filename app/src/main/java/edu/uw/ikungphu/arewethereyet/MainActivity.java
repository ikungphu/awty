package edu.uw.ikungphu.arewethereyet;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Intent alarmIntent;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Are We There Yet?");
    }

    public void setAlarm(View v) {
        EditText text = (EditText) findViewById(R.id.editText);
        String message = text.getText().toString();

        EditText number = (EditText) findViewById(R.id.phone);
        String phone = number.getText().toString();

        EditText interval = (EditText) findViewById(R.id.frequency);
        String seconds = interval.getText().toString();

        int frequency = 0;

        try {
            frequency = Integer.parseInt(seconds);
            if (frequency < 0) {
                Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT);
        }

        alarmIntent.putExtra("message", message);
        alarmIntent.putExtra("phone", phone);

        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        Button button = (Button) findViewById(R.id.start);
        if(button.getText().toString().equals("Start")) {
            if(message != null && phone != null && interval != null) {
                if(frequency > 0 && phone.length() == 10) {
                    start(v);
                    button.setText("Stop")
                }
            }
        } else if (button.getText().toString().equals("Stop")) {
            button.setText("Start");
            cancel(v);
        }
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
