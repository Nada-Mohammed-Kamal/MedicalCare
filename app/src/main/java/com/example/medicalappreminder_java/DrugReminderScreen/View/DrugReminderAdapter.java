package com.example.medicalappreminder_java.DrugReminderScreen.View;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.List;

public class DrugReminderAdapter extends RecyclerView.Adapter<DrugReminderAdapter.DrugReminderViewHolder> {

    private final Context context ;
    private Medicine medicineObject;
    private List<CustomTime> customTimes;
    public class DrugReminderViewHolder extends RecyclerView.ViewHolder {

        public TextView drugTimeTextView , pillsNumberTextView ;
        public ConstraintLayout constraintLayout ;
        public View layout ;

        public DrugReminderViewHolder(View itemView) {
            super(itemView);
            layout = itemView ;
            drugTimeTextView = itemView.findViewById(R.id.drugReminderTimeTextView) ;
            pillsNumberTextView = itemView.findViewById(R.id.pillsNumberReminderTextView) ;
            constraintLayout = itemView.findViewById(R.id.drug_reminder_constraintlayout_row) ;
            Log.e("drugReminderRV", "DrugReminderViewHolder: ");
        }
    }

    public DrugReminderAdapter(Context context, Medicine medicineObject) {
        this.context = context;
        this.medicineObject = medicineObject;
        customTimes = medicineObject.getDoseTimes();
        Log.e("drugReminderRV", "DrugReminderAdapter: ");
    }

    @Override
    public DrugReminderViewHolder onCreateViewHolder( ViewGroup recyclerView , int viewType) {

        LayoutInflater inflater = LayoutInflater.from(recyclerView.getContext()) ;
        View view = inflater.inflate(R.layout.drug_reminder_row , recyclerView , false) ;
        DrugReminderViewHolder viewHolder = new DrugReminderViewHolder(view) ;
        Log.e("drugReminderRV", "***** onCreateViewHolder drug reminder ***** ");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( DrugReminderViewHolder viewHolder, int position) {
        // number of pills in one time
        CustomTime customTime = customTimes.get(position);
        String howOften = ""+medicineObject.getTotalNumOfPills() ;
        // dose times
        viewHolder.drugTimeTextView.setText(customTime.getHour() + ":"+customTime.getMinute());
        viewHolder.pillsNumberTextView.setText("Take " + howOften + " Pill(s)");
        Log.e("drugReminderRV", "onBindViewHolder: ");
    }

    @Override
    public int getItemCount() {
        return customTimes.size() ;

    }

}
