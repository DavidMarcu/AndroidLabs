package com.fiiandroid.lab2;

import android.app.ListActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SensorsActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        List<Map<String, Object>> theList = new ArrayList<>();
        ListAdapter listAdapter;

        for (Sensor sensor : sensors) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("name", sensor.getName());
            listItem.put("sensor", sensor);
            theList.add(listItem);
        }

        String[] from = {"name"};
        int[] to = {R.id.product_name};

        listAdapter = new SimpleAdapter(this, theList, R.layout.item_layout, from, to);
        setListAdapter(listAdapter);
        Sensor maxSensorByPower = Collections.max(sensors, (s1, s2) -> Float.compare(s1.getPower(), s2.getPower()));
        Log.d("SENSOR", maxSensorByPower.toString());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object obj = getListView().getItemAtPosition(position);
        Map<String, Object> itemList = (Map<String, Object>) obj;
        Sensor displayedSensor = (Sensor) itemList.get("sensor");
        TextView productDetails = findViewById(R.id.product_details);
        productDetails.setText(displayedSensor != null ? displayedSensor.toString() : "");
    }
}
