package com.example.medicalappreminder_java.AddMedicine.View.AdapterForTakeDoseTimes;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.CustomTime;

import java.util.ArrayList;
import java.util.Calendar;

public class DoseTimesAdapter extends RecyclerView.Adapter<DoseTimesAdapter.ViewHolder> {

    private final Context context;
    int numberOnDose;
    int count = 1;
    int timeSetsCorrectly = 1;
    ArrayList<CustomTime> customTimes;
    public DoseTimesAdapter(Context context,int numberOnDose) {
        this.context = context;
        this.numberOnDose = numberOnDose;
        customTimes = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dose_times_listview,recyclerView,false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i("Adapter", "=====onCreateViewHolder========= ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDoseTime.setText("Dose "+count++);
        holder.openChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeSetsCorrectly++;
                        holder.openChooseTime.setText(selectedHour + ":" + selectedMinute);
                        customTimes.add(new CustomTime(selectedHour,selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }
    public ArrayList<CustomTime> getTimes()
    {
        if(count != timeSetsCorrectly)
            return  new ArrayList<CustomTime>();
        return customTimes;
    }
    @Override
    public int getItemCount() {
        return numberOnDose;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView setDoseTime;
        public EditText openChooseTime;
        public View layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
            setDoseTime = itemView.findViewById(R.id.setDoseTime);
            openChooseTime = itemView.findViewById(R.id.openChooseTime);
        }
    }
}
