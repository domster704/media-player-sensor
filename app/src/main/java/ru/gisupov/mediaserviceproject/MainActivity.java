package ru.gisupov.mediaserviceproject;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView tvText;
    SensorManager sensorManager;
    Sensor sensorLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = (TextView) findViewById(R.id.tvText);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(listenerLight, sensorLight,
                SensorManager.SENSOR_DELAY_NORMAL);
        startService(new Intent(this, PlayerService.class));
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerLight, sensorLight);
    }

    SensorEventListener listenerLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] < 1000.0f) {  // максимум вроде 5000
                PlayerService.contMplayer();
            } else {
                PlayerService.pauseMplayer();
            }
            tvText.setText(String.valueOf(event.values[0]));
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, PlayerService.class));
    }

}