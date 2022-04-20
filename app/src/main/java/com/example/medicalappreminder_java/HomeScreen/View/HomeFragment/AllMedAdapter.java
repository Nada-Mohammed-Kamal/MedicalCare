package com.example.medicalappreminder_java.HomeScreen.View.HomeFragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


//AllMoviesAdapter
public class AllMedAdapter extends RecyclerView.Adapter<AllMedAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Medicine> medList;
    OnMoviesClickListener onMoviesClickListener;

    public AllMedAdapter(Context context, ArrayList<Medicine> values, OnMoviesClickListener onMoviesClickListener) {
        this.context = context;
        this.medList = values;
        this.onMoviesClickListener = onMoviesClickListener;
    }
    public  void setList(List<Medicine> updateMeds){
        this.medList = (ArrayList)updateMeds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.show_all_med_custom_row,recyclerView,false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i("Adapter", "=====onCreateViewHolder========= ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Medicine medDTO = medList.get(position);
        String decMed = ""+ medDTO.getStrengthAmount() + medDTO.getStrength() + ",take " +medDTO.getForm();
       // holder.medTime.setText(medDTO.get);
        holder.medDesc.setText(decMed);
        holder.medName.setText(medDTO.getName());
        holder.medIcon.setImageResource(medDTO.getImage());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onMoviesClickListener.onClick(medDTO);
                Toast.makeText(context,"Med clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return medList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView medIcon;
        TextView medTime;
        TextView medName;
        TextView medDesc;
        public  View layout;
        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView;
           medIcon = itemView.findViewById(R.id.medIconRecylerViewHomeScreen);
           medTime = itemView.findViewById(R.id.doseTimeRecylerViewHomeScreen);
           medName = itemView.findViewById(R.id.medNameRecylerViewHomeScreen);
           medDesc = itemView.findViewById(R.id.medDescriptionRecylerViewHomeScreen);
        }
    }
}

