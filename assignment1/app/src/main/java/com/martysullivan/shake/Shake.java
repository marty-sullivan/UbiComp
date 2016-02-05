package com.martysullivan.shake;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

public class Shake extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mBarometer;

    private Button btnStart, btnStop, btnPressure;
    private TextView txtAccel, txtDetect, txtPressure;
    private EditText editThreshold;

    private Shake self = this;
    private double threshold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        btnStart = (Button)findViewById(R.id.btnStart);
        btnStop = (Button)findViewById(R.id.btnStop);
        btnPressure = (Button)findViewById(R.id.btnPressure);
        txtAccel = (TextView)findViewById(R.id.txtAccel);
        txtDetect = (TextView)findViewById(R.id.txtDetect);
        txtPressure = (TextView)findViewById(R.id.txtPressure);
        editThreshold = (EditText)findViewById(R.id.editThreshold);

        // Set Start Button Listener
        btnStart.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    threshold = Double.parseDouble(editThreshold.getText().toString());
                    mSensorManager.registerListener(self, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                }
                catch (Exception e) {
                    txtDetect.setText("Error: Set Threshold to a decimal value.");
                }
            }
        });

        // Set Stop Button Listener
        btnStop.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSensorManager.unregisterListener(self);
                txtAccel.setText("");
                txtDetect.setText("");
            }
        });

        btnPressure.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mSensorManager.registerListener(self, mBarometer, SensorManager.SENSOR_DELAY_NORMAL);
            }
        });

        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mBarometer = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shake, menu);
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


    private boolean isShaking(float[] axes, double threshold) {
        boolean shaking = false;

        if (Math.sqrt(Math.pow(axes[0], 2) + Math.pow(axes[1], 2) + Math.pow(axes[2], 2)) >= threshold) {
            shaking = true;
        }

        return shaking;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            txtAccel.setText(Arrays.toString(event.values));
            if (this.isShaking(event.values, threshold)) {
                txtDetect.setText("Shake");
            }
            else {
                txtDetect.setText("No Shake");
            }
        }
        else if (event.sensor.getType() == Sensor.TYPE_PRESSURE) {
            txtPressure.setText(Float.toString(event.values[0]));
            mSensorManager.unregisterListener(this);
            txtAccel.setText("");
            txtDetect.setText("");
        }
    }

}
