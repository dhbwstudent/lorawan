/*
 *      @author
 *      Nicola Cicio, Simon Merkt
 *
 *      @description
 *      These are Helper-Classes for building the Views correct
 *      and putting the right Data in the result View based on the Button which is selected
 *
 */
package com.example.parser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.parse.ParseObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ResultAdapter extends RecyclerView.Adapter<ResultHolder> {

    private Context fieldcontext;
    private List<ParseObject> datalist;

    public ResultAdapter(Context context, List<ParseObject> list) {
        this.datalist = list;
        this.fieldcontext = context;
    }

    // If a new Result Holder is created this Class will be called
    @NonNull
    @Override
    public ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View sensorcontent = LayoutInflater.from(fieldcontext).inflate(R.layout.datafield, parent,false);
        return new ResultHolder(sensorcontent);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ResultHolder holder, int position) {
        ParseObject Sensordata = datalist.get(position);


        // Put Data of the LoRa Sensor in Adapter
        if (MainActivity.card11.isSelected()){

            // Query Data column
            Date dateval = Sensordata.getCreatedAt();
            Number card1 = Sensordata.getNumber("temperature");
            Number card2 = Sensordata.getNumber("light");
            Number card3 = Sensordata.getNumber("humidity");
            Number card4 = Sensordata.getNumber("co2");

            // Convert Date in another Format
            String dateformat = "dd.MM.yy - HH:mm";
            DateFormat newformat = new SimpleDateFormat(dateformat);
            String dateAsString = newformat.format(dateval);

            // Selections for the LoRaSensor
            if (MainActivity.temparaturebutton.isSelected()){
                holder.show_time.setText(dateAsString);
                holder.show_sensordata.setText(String.valueOf(card1));
            }

            if (MainActivity.lightbutton.isSelected()){
                holder.show_time.setText(dateAsString);
                holder.show_sensordata.setText(String.valueOf(card2));
            }

            if (MainActivity.humiditybutton.isSelected()){
                holder.show_time.setText(dateAsString);
                holder.show_sensordata.setText(String.valueOf(card3));
            }

            if (MainActivity.co2button.isSelected()){
                holder.show_time.setText(dateAsString);
                holder.show_sensordata.setText(String.valueOf(card4));
            }
        }

        // Put Data of the Soil Moisture Sensor in Adapter
        if (MainActivity.card22.isSelected()) {
            Number soilval = Sensordata.getNumber("humidity");
            Date dateval2 = Sensordata.getCreatedAt();

            // Convert Date in another Format
            String dateformat = "dd.MM.yy - HH:mm";
            DateFormat newformat = new SimpleDateFormat(dateformat);
            String dateAsString = newformat.format(dateval2);

            holder.show_time.setText(dateAsString);
            holder.show_sensordata.setText(String.valueOf(soilval));
        }
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }
}

// Set the List-Format to the defined Structure in the datafield-Layout
class ResultHolder extends RecyclerView.ViewHolder {
    TextView show_sensordata;
    TextView show_time;

    public ResultHolder(@NonNull View itemView) {
        super(itemView);
        show_sensordata = itemView.findViewById(R.id.sensordata);
        show_time = itemView.findViewById(R.id.time);
    }

}