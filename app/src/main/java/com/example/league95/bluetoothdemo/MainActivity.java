package com.example.league95.bluetoothdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    /*Start by creating a Bluetooth Adapter*/
    BluetoothAdapter BA;
    //ListView
    ListView listView;
    ArrayAdapter arrayAdapter;
    Button ba;

    public void turnBluetoothOff(View view)
    {
        if (BA.isEnabled()){
            Toast.makeText(this, "Bluetooth Off!", Toast.LENGTH_LONG).show();
            BA.disable();
            ba.setText("Turn Bluetooth On");

        } else {
            BA.enable();
            Toast.makeText(this, "Bluetooth On!", Toast.LENGTH_LONG).show();
            ba.setText("Turn Bluetooth Off");
        }

    }

    public void findDevices(View view)
    {
        Intent intent = new Intent(BA.ACTION_REQUEST_DISCOVERABLE);
        startActivity(intent);
        ba.setText("Turn Bluetooth Off");
    }

    public void viewPairedDevices(View view)
    {
        //We are going to need a set, to avoid duplicates!
        Set<BluetoothDevice> pairedDevices= BA.getBondedDevices();
        listView = findViewById(R.id.listViewDevices);

        ArrayList pairedDevicesArrayList = new ArrayList();
        pairedDevicesArrayList.clear();

        for (BluetoothDevice bluetoothDevice : pairedDevices)
        {
            pairedDevicesArrayList.add(bluetoothDevice.getName());
        }
        if (!BA.isEnabled()) {
            Toast.makeText(this, "Turn on Bluetooth!", Toast.LENGTH_SHORT).show();
            pairedDevicesArrayList.clear();
            arrayAdapter.notifyDataSetChanged();
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,pairedDevicesArrayList);
        listView.setAdapter(arrayAdapter);
        
        

        

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ba = findViewById(R.id.baOff);

        BA = BluetoothAdapter.getDefaultAdapter();

        if (BA.isEnabled()) {
            Toast.makeText(this, "Bluetooth On", Toast.LENGTH_LONG).show();
        } else {
            //Attempt to enable bluetooth
            Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(i);
            if (BA.isEnabled()) {
                Toast.makeText(this, "Bluetooth On after permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
