package com.example.medicalappreminder_java.MedModification.View;

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

import com.example.medicalappreminder_java.LoginTest;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DosesAdapter extends RecyclerView.Adapter<DosesAdapter.ViewHolder> {

    private final Context context;

    Medicine med;

    int numberOnDose;
    int count = 1;
    int timeSetsCorrectly = 1;
    ArrayList<CustomTime> dateTimes;


    public DosesAdapter(Context context, int numberOnDose, Medicine med) {
        this.context = context;
        this.numberOnDose = numberOnDose;
        this.med = med;
        this.dateTimes = new ArrayList<>();

        Log.i("M3lsh", "Adapter: dateTimes size: " + dateTimes.size());
    }

    @Override
    public DosesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dose_times_listview,recyclerView,false);

        DosesAdapter.ViewHolder viewHolder = new DosesAdapter.ViewHolder(view);
        Log.i("Adapter", "=====onCreateViewHolder========= ");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DosesAdapter.ViewHolder holder, int position) {
        Log.i("M3lsh", "onBindViewHolder: ");
        List<CustomTime> doses = med.getDoseTimes();
        int index = position;

        Log.i("M3lsh","Before: Index: " + index + ", Doses.size(): " + doses.size() + ", dateTimes.size(): " + dateTimes.size());
        if (index < doses.size()) {


            CustomTime currentTime = doses.get(index);
            String valueToBeAdded = String.valueOf(currentTime.getHour()) + ":" + String.valueOf(currentTime.getMinute());
            holder.openChooseTime.setText(valueToBeAdded);
            if (index < dateTimes.size())
            {
                dateTimes.set(index, new CustomTime(currentTime.getHour(), currentTime.getMinute()));
            }
            else
            {
                Log.i("M3lsh","Added custom time successfully, from: If condition");
                dateTimes.add(new CustomTime(currentTime));
            }
        }


        holder.setDoseTime.setText("Dose #" + count++);
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
                        if (index < dateTimes.size())
                        {
                            dateTimes.set(index, new CustomTime(selectedHour,selectedMinute));
                        }
                        else
                        {
                            Log.i("M3lsh","Added custom time successfully, from TimeSet");
                            dateTimes.add(new CustomTime(selectedHour,selectedMinute));
                        }
                    }
                }, hour, minute, true);

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });



    }

    public ArrayList<CustomTime> getTimes() {
        if(count != timeSetsCorrectly)
            return  new ArrayList<CustomTime>();
        return dateTimes;
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
            Log.i("M3lsh", "ViewHolder: ");
        }
    }

    public void configureList() {
        dateTimes = new ArrayList<CustomTime>();
    }

    public ArrayList<CustomTime> getDateList() {
        System.out.println("DateTimes returned from adapter size: " + this.dateTimes.size());
        return this.dateTimes;
    }
}
