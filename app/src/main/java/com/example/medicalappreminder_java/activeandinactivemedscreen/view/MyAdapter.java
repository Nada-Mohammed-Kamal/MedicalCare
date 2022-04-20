package com.example.medicalappreminder_java.activeandinactivemedscreen.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {
    List<Medicine> data;
    Context context;
    public MyAdapter(Context context , List<Medicine> data){
        this.data = data;
        this.context = context;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView ageTextView;
        TextView emailTextView;
        ImageView imageView;
        View row;
        public myViewHolder(@NonNull View itemView ) {
            super(itemView);
            row = itemView;
            nameTextView = row.findViewById(R.id.nameTextViewId);
            ageTextView = row.findViewById(R.id.ageTextViewId);
            emailTextView = row.findViewById(R.id.emailTextViewId);
            imageView = row.findViewById(R.id.imageView);
        }
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyAdapter myAdapter = new MyAdapter(context ,data);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        myViewHolder viewHolder = myAdapter.new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.nameTextView.setText(data.get(position).getName());
        holder.ageTextView.setText(""+ data.get(position).getStrengthAmount()+ " "  +data.get(position).getStrength() );
        holder.emailTextView.setText(""+data.get(position).getNumberOfPillsLeft() + " Pill(s) left" );
        holder.imageView.setImageResource(data.get(position).getImage());
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context , "" + holder.nameTextView.getText().toString() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
