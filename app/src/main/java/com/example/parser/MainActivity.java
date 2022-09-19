/*
 *      @author
 *      Nicola Cicio, Simon Merkt
 *
 *      @description
 *      This Class contains the different functions to receive or send Data
 *
 */
package com.example.parser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public static ImageButton temparaturebutton;
    public static ImageButton lightbutton;
    public static ImageButton humiditybutton;
    public static ImageButton co2button;
    public static ImageButton soilbtn;
    public static View card11;
    public static View card22;
    private ImageView back;
    private ResultAdapter datablock;
    private RecyclerView resultList;
    private RecyclerView resultList2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // set View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);

        selection();
    }


    // Selection in the Overview Layout
    private void selection() {
        setContentView(R.layout.overview);

        card11 = findViewById(R.id.card1);
        card22 = findViewById(R.id.card2);
        View card3 = findViewById(R.id.card3);
        View card4 = findViewById(R.id.card4);

        card11.setSelected(false);
        card22.setSelected(false);

        card11.setOnClickListener(view -> {
            card11.setSelected(true);
            getLoRaData();
        });


        card22.setOnClickListener(view -> {
            card22.setSelected(true);
            getSoilData();
        });

        card3.setOnClickListener(view -> {
            setoverview();
        });


        card4.setOnClickListener(view -> {
            setPumpData();
        });
    }


    // Configuration for the View of the LoRa Sensor
    public void getLoRaData() {

        setContentView(R.layout.lorasensor);

        // Set-Up return  Button
        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            selection();
        });

        // On Click Listener for the different Selections
        temparaturebutton = findViewById(R.id.tempbtn);
        lightbutton = findViewById(R.id.lightbtn);
        humiditybutton = findViewById(R.id.humidbtn);
        co2button = findViewById(R.id.co2btn);
        resultList = findViewById(R.id.resultList);


        // Set which Button is currently Selected
        temparaturebutton.setOnClickListener(view -> {
            temparaturebutton.setSelected(true);
            lightbutton.setSelected(false);
            humiditybutton.setSelected(false);
            co2button.setSelected(false);

            queryData();
        });

        lightbutton.setOnClickListener(view -> {
            lightbutton.setSelected(true);
            temparaturebutton.setSelected(false);
            humiditybutton.setSelected(false);
            co2button.setSelected(false);

            queryData();
        });

        humiditybutton.setOnClickListener(view -> {
            humiditybutton.setSelected(true);
            lightbutton.setSelected(false);
            temparaturebutton.setSelected(false);
            co2button.setSelected(false);

            queryData();
        });

        co2button.setOnClickListener(view -> {
            co2button.setSelected(true);
            lightbutton.setSelected(false);
            temparaturebutton.setSelected(false);
            humiditybutton.setSelected(false);

            queryData();
        });

    }

    // Query Function for LoRa Sensor
    public void queryData(){
        ParseQuery<ParseObject> Sensor_query = new ParseQuery<>("Sensordata");

        Sensor_query.findInBackground((objects, e) -> {
            if (e == null) {
                // get data from defined Adapter
                datablock = new ResultAdapter(this, objects);

                // set data in result-list to show them
                resultList.setLayoutManager(new LinearLayoutManager(this));
                resultList.setAdapter(datablock);

            } else {
                // print Pop-Up Message for User
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    // Query Function for Soil Moisture Sensor
    public void queryData2(){
        resultList2 = findViewById(R.id.resultList22);

        ParseQuery<ParseObject> Sensor_query = new ParseQuery<>("ESP32");

        Sensor_query.findInBackground((objects, e) -> {
            if (e == null) {
                // get data from defined Adapter
                datablock = new ResultAdapter(this, objects);

                // set data in result-list to show them
                resultList2.setLayoutManager(new LinearLayoutManager(this));
                resultList2.setAdapter(datablock);

            } else {
                // print Pop-Up Message for User
                Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setoverview(){
        setContentView(R.layout.projectoverview);

        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            selection();
        });
    }


    // Set Up the View of the Soil Moisture Sensor
    private void getSoilData() {
        setContentView(R.layout.soilsensor);

        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            selection();
        });

        soilbtn = findViewById(R.id.queryData22);

        soilbtn.setOnClickListener(view -> {
            soilbtn.setSelected(true);

            queryData2();
        });
    }

    // Trigger Cloud Function which send Data to the Soil Sensor
    private void setPumpData() {
        ParseCloud.callFunctionInBackground("DownlinkESP", new HashMap(), (FunctionCallback<String>) (string, e) -> {
            if (e == null) {
                Log.d("result", string);

                Context context = getApplicationContext();
                CharSequence text = "Pumpe wurde gestartet!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            } else {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        selection();
    }
}