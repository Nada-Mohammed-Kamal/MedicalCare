package com.example.medicalappreminder_java.activeandinactivemedscreen.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.activeandinactivemedscreen.model.Section;
import com.example.medicalappreminder_java.activeandinactivemedscreen.presenter.ActiveInactivePresenter;
import com.example.medicalappreminder_java.activeandinactivemedscreen.PresenterInterface;
import com.example.medicalappreminder_java.models.Medicine;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MyFirstFragment extends Fragment {
    RecyclerView mRecyclerView;
    List<Section> sections;
    View view;
    PresenterInterface activeInactivePresenter;

    public MyFirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Your RecyclerView
        activeInactivePresenter = new ActiveInactivePresenter(getContext());
        initData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.RecyclerViewInsideTheFragmentid);
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(getContext() , sections);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mainRecyclerAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ExtendedFloatingActionButton e = getActivity().findViewById(R.id.ExtendedFloatingActionButtonAddMed);
        if(e != null)
            e.setVisibility(View.VISIBLE);
        view = inflater.inflate(R.layout.fragment_my_first_frgment, container, false);
        return view;
    }

    //hena b add bs shwayat data static
    private void initData(){
        String sectionOneName = "Active";
        List<Medicine> activeMeds = new ArrayList<>();
        List<Medicine> inactive = new ArrayList<>();
        List<Medicine> currentUserMeds = activeInactivePresenter.getCurrentUserMeds();
        for (Medicine med:currentUserMeds) {
            String state = med.getState();
            if(state.equals("Active")){
                activeMeds.add(med);
            } else if (state.equals("Inactive")){
                inactive.add(med);
            }
        }
        
        List<Medicine> sectionOneItems = new ArrayList<>();
        sectionOneItems = activeMeds;

        String sectionTwoName = "Inactive";
        List<Medicine> sectionTwoItems = new ArrayList<>();
        sectionTwoItems = inactive;


        sections = new ArrayList<>();
        sections.add(new Section(sectionOneName,sectionOneItems));
        sections.add(new Section(sectionTwoName,sectionTwoItems));

    }

}