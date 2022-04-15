package com.example.medicalappreminder_java.activeandinactivemedscreen.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalappreminder_java.Constants.Form;
import com.example.medicalappreminder_java.Constants.Strength;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.activeandinactivemedscreen.model.Section;
import com.example.medicalappreminder_java.models.Medicine;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyFirstFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<Section> sections;
    View view;

    public MyFirstFragment() {
        // Required empty public constructor
        initData();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Your RecyclerView


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.RecyclerViewInsideTheFragmentid);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(getContext() , sections);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mainRecyclerAdapter);
        //for the fragment navigation

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_first_frgment, container, false);
        return view;
    }

    //hena b add bs shwayat data static
    private void initData(){
        String sectionOneName = "Active";
        List<Medicine> sectionOneItems = new ArrayList<>();
        sectionOneItems.add(new Medicine(UUID.randomUUID() ,"panadol" , Form.Pill , Strength.g , 5 , 7,R.drawable.pill));
        sectionOneItems.add(new Medicine(UUID.randomUUID() ,"panadrex" , Form.Pill , Strength.g , 3 , 10,R.drawable.pill));
        sectionOneItems.add(new Medicine(UUID.randomUUID(), "drips" , Form.Drops , Strength.mcg_ml , 2 , 10,R.drawable.drops));

        String sectionTwoName = "Inactive";
        List<Medicine> sectionTwoItems = new ArrayList<>();
        sectionTwoItems.add(new Medicine(UUID.randomUUID() ,"catafast" , Form.Pill , Strength.g , 5 , 7,R.drawable.pill));
        sectionTwoItems.add(new Medicine(UUID.randomUUID() ,"cataflam" , Form.Pill , Strength.g , 6 , 9,R.drawable.pill));


        sections = new ArrayList<>();
        sections.add(new Section(sectionOneName,sectionOneItems));
        sections.add(new Section(sectionTwoName,sectionTwoItems));

        String TAG = "TAG";
        Log.i(TAG, "initData: " + sections);

    }

}