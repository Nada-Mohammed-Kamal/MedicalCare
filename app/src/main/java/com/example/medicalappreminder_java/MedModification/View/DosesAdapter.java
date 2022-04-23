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

        List<CustomTime> doses = med.getDoseTimes();

        if (position < doses.size())
        {
            String valueToBeAdded = String.valueOf(doses.get(position).getHour()) + ":" + String.valueOf(doses.get(position).getMinute());
            holder.openChooseTime.setText(valueToBeAdded);
            dateTimes.add(new CustomTime(doses.get(position)));
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
                        if (position < doses.size())
                        {
                            dateTimes.set(position, new CustomTime(selectedHour,selectedMinute));
                        }
                        else
                        {
                            dateTimes.add(new CustomTime(selectedHour,selectedMinute));
                        }

                    }
                }, hour, minute, true);

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


    }

    public ArrayList<CustomTime> getTimes()
    {
        if(count != timeSetsCorrectly)
            return  new ArrayList<CustomTime>();
        return  dateTimes;
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

    public void clearList()
    {
        dateTimes.clear();
    }

    public List<CustomTime> getNewDateList()
    {
        return dateTimes;
    }
}
