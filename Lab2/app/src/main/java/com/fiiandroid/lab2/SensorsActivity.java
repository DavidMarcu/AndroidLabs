package com.fiiandroid.lab2;

import android.app.ListActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorsActivity extends ListActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private List<Map<String, Object>> theList;

    @Override
    public void onSensorChanged(SensorEvent event) {
        for (int i = 0; i < theList.size(); i++) {
            if(event.sensor.equals(theList.get(i).get("sensor"))) {
                theList.get(i).put("sensor_value", Arrays.toString(event.values));
//                Log.d("SENSOR", theList.get(i).get("sensor").toString() + (theList.get(i).get("sensor_value")!=null ? theList.get(i).get("sensor_value") : " ------- nil"));
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        theList = new ArrayList<>();

        for(Sensor sensor : sensors){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                Map<String, Object> listItem = new HashMap<>();
                listItem.put("name", sensor.getName());
                listItem.put("sensor", sensor);
                List<Float> floats = new ArrayList<>();
//                for (float f : event.values)
//                    floats.add(f);
//                listItem.put("sensor_values", floats.toString());
                theList.add(listItem);
            }
            ListAdapter listAdapter;
            String[] from = {"name"};
            int[] to = {R.id.product_name};

            listAdapter = new SimpleAdapter(this, theList, R.layout.item_layout, from, to);
            setListAdapter(listAdapter);
//        Sensor maxSensorByPower = Collections.max(sensors, (s1, s2) -> Float.compare(s1.getPower(), s2.getPower()));
//        Log.d("SENSOR", maxSensorByPower.toString());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object obj = getListView().getItemAtPosition(position);
        Map<String, Object> itemList = (Map<String, Object>) obj;
        Sensor displayedSensor = (Sensor) itemList.get("sensor");

        TextView sensorDetails = findViewById(R.id.sensor_details);
        sensorDetails.setText(displayedSensor != null ? displayedSensor.toString() : "");

        TextView sensorValues = findViewById(R.id.sensor_values);
        sensorValues.setText((String)itemList.get("sensor_value"));
    }
}
