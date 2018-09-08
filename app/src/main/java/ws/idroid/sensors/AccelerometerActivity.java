package ws.idroid.sensors;

import android.content.Context;
import android.hardware.*;
import android.os.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.*;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {

    TextView currentX, currentY, currentZ, maxX, maxY, maxZ;
    private float lastX = 0, lastY = 0, lastZ = 0;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private float deltaXMax = 0;
    private float deltaYMax = 0;
    private float deltaZMax = 0;
    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;
    private float vibrateThreshold = 0;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        //initializing the Views
        initViews();
        initSensorManager();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void initSensorManager() {
//       Context context = new Cont

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            vibrateThreshold = accelerometer.getMaximumRange() / 2;
        } else {
            Toast.makeText(AccelerometerActivity.this, "Sorry your device does not have this " +
                    "Sensor!", Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        currentX = findViewById(R.id.currentX);
        currentY = findViewById(R.id.currentY);
        currentZ = findViewById(R.id.currentZ);
        maxX = findViewById(R.id.maxX);
        maxY = findViewById(R.id.maxY);
        maxZ = findViewById(R.id.maxZ);
    }

    private void displayCleanValues() {
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");
    }

    private void displayMaxValues() {
        if (deltaX > deltaXMax) {
            deltaXMax = deltaX;
            maxX.setText(String.valueOf(deltaXMax));
        }
        if (deltaY > deltaYMax) {
            deltaYMax = deltaY;
            maxY.setText(String.valueOf(deltaYMax));
        }
        if (deltaZ > deltaZMax) {
            deltaZMax = deltaZ;
            maxZ.setText(String.valueOf(deltaZMax));
        }

    }

    private void displayCurrentValues() {

        currentX.setText(String.valueOf(deltaX));
        currentY.setText(String.valueOf(deltaY));
        currentZ.setText(String.valueOf(deltaZ));

    }

    protected void OnResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void OnPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
//fired once the sensor sends any change in the data
        //clean current values
        displayCleanValues();

        //displayCurrent values
        displayCurrentValues();
        //Max values
        displayMaxValues();
        deltaX = Math.abs(lastX - sensorEvent.values[0]);
        deltaY = Math.abs(lastY - sensorEvent.values[1]);
        deltaZ = Math.abs(lastZ - sensorEvent.values[2]);
        if (deltaX < 2)
            deltaX = 0;
        if (deltaY < 2)
            deltaY = 0;
        if (deltaY < 2)
            deltaY = 0;
        if ((deltaZ > vibrateThreshold) || (deltaX > vibrateThreshold) || (deltaY >
                vibrateThreshold)) {
            vibrator.vibrate(1000);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
