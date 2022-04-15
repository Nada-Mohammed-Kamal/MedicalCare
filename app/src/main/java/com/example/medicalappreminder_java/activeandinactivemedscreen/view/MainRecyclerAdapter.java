package com.example.medicalappreminder_java.activeandinactivemedscreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.activeandinactivemedscreen.model.Section;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.List;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.myViewHolder>{
    Context context;
    List<Section> sectionList;
    public MainRecyclerAdapter(Context context , List<Section>  data){
        this.context = context;
        sectionList = data;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView sectionTitle;
        RecyclerView sectionRecyclerView;
        View row;
        public myViewHolder(@NonNull View itemView ) {
            super(itemView);
            row = itemView;
            sectionTitle = row.findViewById(R.id.oneSectionTitleId);
            sectionRecyclerView = row.findViewById(R.id.oneSectionRecyclerViewId);
        }
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainRecyclerAdapter myAdapter = new MainRecyclerAdapter(context ,sectionList);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.section_row, parent, false);
        myViewHolder viewHolder = myAdapter.new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Section section = sectionList.get(position);
        String sectionName = section.getSectionName();
        List<Medicine> items = section.getSectionItems();
        holder.sectionTitle.setText(sectionName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        holder.sectionRecyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter myAdapter = new MyAdapter(context , items);
        holder.sectionRecyclerView.setAdapter(myAdapter);

    }


    @Override
    public int getItemCount() {
        return sectionList.size();
    }
}
