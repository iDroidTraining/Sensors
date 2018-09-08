package ws.idroid.sensors;

import android.content.Intent;
import android.hardware.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvResult = findViewById(R.id.txt_result);
        tvResult.setMovementMethod(new ScrollingMovementMethod());
        //Sensor Manager
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> mList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < mList.size(); i++) {
            tvResult.append("\n" + mList.get(i).getName() + "\n" + mList.get(i).getVendor() +
                    "\n" + mList.get(i).getVersion());
        }
    }

    public void openSecondExample(View view) {
        startActivity(new Intent(MainActivity.this, AccelerometerActivity.class));
    }
}
