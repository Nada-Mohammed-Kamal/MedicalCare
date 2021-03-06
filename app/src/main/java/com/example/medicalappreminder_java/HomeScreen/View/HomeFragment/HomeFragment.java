package com.example.medicalappreminder_java.HomeScreen.View.HomeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.medicalappreminder_java.HomeScreen.Presenter.HomeFragment.AllMedPresenter;
import com.example.medicalappreminder_java.HomeScreen.Presenter.HomeFragment.AllMedPresenterInterface;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class HomeFragment extends Fragment implements OnMoviesClickListener,AllMedViewInterface{

    RecyclerView recyclerView;
    AllMedAdapter myAdapter;
    LinearLayoutManager layoutManager;
    AllMedPresenterInterface allPresenter;
    Calendar selectedDate;
    Date dateSelected;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        selectedDate = Calendar.getInstance();
        allPresenter = new AllMedPresenter(this,getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ExtendedFloatingActionButton e = getActivity().findViewById(R.id.ExtendedFloatingActionButtonAddMed);
        if(e != null)
            e.setVisibility(View.VISIBLE);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);


        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        View calView = view.findViewById(R.id.calendarView);
        // on below line we are setting up our horizontal calendar view and passing id our calendar view to it.
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, calView.getId())
                // on below line we are adding a range
                // as start date and end date to our calendar.
                .range(startDate, endDate)
                // on below line we are providing a number of dates
                // which will be visible on the screen at a time.
                .datesNumberOnScreen(7)

                // at last we are calling a build method
                // to build our horizontal recycler view.
                .build();
        // on below line we are setting calendar listener to our calendar view.
        horizontalCalendar.selectDate(selectedDate,true);
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {

            @Override
            public void onDateSelected(java.util.Calendar d, int position) {

                d.add(Calendar.DATE, 1);
                dateSelected = changeDateFormat(d.getTime());

                Log.e("date", "onDateSelected: "+dateSelected.toString());
                allPresenter.getMeds(dateSelected);

            };

//            @Override
//            public boolean onDateLongClicked(Calendar date, int position) {
//                dateSelected = changeDateFormat(selectedDate.getTime());
//                Log.e("date", "onDateSelecte22d: "+dateSelected.toString());
//                return super.onDateLongClicked(date, position);
//            }
        });




        recyclerView = view.findViewById(R.id.recyclerViewAllMed);
        myAdapter = new AllMedAdapter(getContext(),new ArrayList<>(),new ArrayList<>(),this,allPresenter);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void showData(List<Medicine> medsList, List<CustomTime> customTimes) {
        myAdapter.setList(medsList,customTimes);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();

       dateSelected =  changeDateFormat(selectedDate.getTime());

        allPresenter.getMeds(dateSelected);
    }

    @Override
    public void onClick(Medicine medDTO) {
        Toast.makeText(getContext(), "Med pressed",Toast.LENGTH_SHORT).show();
    }
    private Date changeDateFormat(Date currentDate)
    {   Date time = currentDate;
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        try {
            time = dateFormat.parse(dateFormat.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}