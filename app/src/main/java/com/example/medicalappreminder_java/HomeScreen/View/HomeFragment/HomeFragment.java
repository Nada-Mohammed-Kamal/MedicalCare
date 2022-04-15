package com.example.medicalappreminder_java.HomeScreen.View.HomeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.medicalappreminder_java.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        Calendar defaultDate = Calendar.getInstance();
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
        horizontalCalendar.goToday(true);
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {

            @Override
            public void onDateSelected(java.util.Calendar date, int position) {
                Calendar selectedDate = horizontalCalendar.getSelectedDate();

                Toast.makeText(getActivity(),getSelectedDate(selectedDate),Toast.LENGTH_SHORT).show();
            };

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                horizontalCalendar.selectDate(date,false);
                return super.onDateLongClicked(date, position);
            }
        });
    }
    private  String getSelectedDate(Calendar selectedDate)
    {
        Date dateToString = selectedDate.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = format1.format(dateToString );
        return date1;
    }
}