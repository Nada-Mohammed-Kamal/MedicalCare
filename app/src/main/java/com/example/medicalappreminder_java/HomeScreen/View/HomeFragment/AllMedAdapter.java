package com.example.medicalappreminder_java.HomeScreen.View.HomeFragment;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.example.medicalappreminder_java.Constants.OnRespondToMethod;
import com.example.medicalappreminder_java.Constants.Status;
import com.example.medicalappreminder_java.NotificationDialog.OnlineUsers;

import com.example.medicalappreminder_java.HomeScreen.Presenter.HomeFragment.AllMedPresenterInterface;

import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.RepoInterface;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

import com.example.medicalappreminder_java.networkConnectivity.NetworkChangeReceiver;

import com.example.medicalappreminder_java.roomdb.UserData;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import de.hdodenhof.circleimageview.CircleImageView;


//AllMoviesAdapter
public class AllMedAdapter extends SectionedRecyclerViewAdapter<AllMedAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Medicine> medList;
    OnMoviesClickListener onMoviesClickListener;
    List<CustomTime> todayesTimesOfDoses;
    private List<Medicine> sectionMedicines;
    private List<List<Medicine>> allSectionsMedicines;
    AllMedPresenterInterface allMedViewPresenter;
    Dialog dialog ;
    int count = 0;
    String userEmail;

    RepoInterface repoClass;
    public AllMedAdapter(Context context, ArrayList<Medicine> values, List<CustomTime>  todayesTimesOfDoses , OnMoviesClickListener onMoviesClickListener, AllMedPresenterInterface allMedViewPresenter) {
        this.context = context;
        this.medList = values;
        this.onMoviesClickListener = onMoviesClickListener;
        dialog = new Dialog(context);
        this.todayesTimesOfDoses =  todayesTimesOfDoses;
        sectionMedicines = new ArrayList<>();
        allSectionsMedicines = new ArrayList<>();
        this.allMedViewPresenter = allMedViewPresenter;
    }

    @Override
    public int getSectionCount() {
        return todayesTimesOfDoses.size();
    }

    @Override
    public int getItemCount(int section) {
        if(count < todayesTimesOfDoses.size()) {
            sectionMedicines = UserData.getSectionMedicines(medList, todayesTimesOfDoses.get(section));
            allSectionsMedicines.add(sectionMedicines);
            count++;
        }
        return sectionMedicines.size();
    }

    @Override
    public void onBindHeaderViewHolder(AllMedAdapter.ViewHolder holder, int section) {
        holder.medTime.setText(todayesTimesOfDoses.get(section).getHour()+":"+todayesTimesOfDoses.get(section).getMinute());

    }

    @Override
    public void onBindViewHolder(AllMedAdapter.ViewHolder holder, int section, int relativePosition, int absoultePosition) {
        List<Medicine> currentSectionMed = allSectionsMedicines.get(section);
        Medicine medDTO = currentSectionMed.get(relativePosition);
        String decMed = ""+ medDTO.getStrengthAmount() + medDTO.getStrength() + ",take " +medDTO.getForm();
        //holder.medTime.setText(medDTO.get);
        holder.medDesc.setText(decMed);
        holder.medName.setText(medDTO.getName());
        holder.medIcon.setImageResource(medDTO.getImage());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onMoviesClickListener.onClick(medDTO);
                Toast.makeText(context,"Med clicked",Toast.LENGTH_SHORT).show();
                //change with current time of this med
                openDialoge(medDTO,todayesTimesOfDoses.get(section));

            }
        });
    }

    @NonNull
    @Override
    public AllMedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(viewType == VIEW_TYPE_HEADER?R.layout.home_screen_header:R.layout.show_all_med_custom_row,recyclerView,false);
        ViewHolder viewHolder = new ViewHolder(view);
        Log.i("Adapter", "=====onCreateViewHolder========= ");
        return viewHolder;
    }
    public  void setList(List<Medicine> updateMeds,List<CustomTime> customTimes){
        todayesTimesOfDoses = customTimes;
        this.medList = (ArrayList)updateMeds;
        count = 0;
        allSectionsMedicines.clear();
    }
    private void displayNotification (String id, String task, String  desc, int imgID){

        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channelName;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(imgID)
                .setContentTitle(task)
                .setContentText(desc)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(Integer.valueOf(id), builder.build());

        Log.i("M3lsh", "displaying notification" );

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
    private void openDialoge(Medicine medicine,CustomTime currentTime) {
        dialog.setContentView(R.layout.drug_notification_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageButton skipButton , takeButton , snoozeButton ;
        TextView timeTextView , drugNameTextView  ,drugDescrTextView ;
        CircleImageView drugIconImageView ;

        skipButton = dialog.findViewById(R.id.dialogSkipButton) ;
        takeButton = dialog.findViewById(R.id.dialogTakeButton);
        snoozeButton = dialog.findViewById(R.id.dialogSnoozeButton) ;
        timeTextView = dialog.findViewById(R.id.dialogTimeTextView) ;
        drugNameTextView = dialog.findViewById(R.id.dialogDrugNameTextView) ;
        drugDescrTextView = dialog.findViewById(R.id.dialogDrugDescrTextView) ;
        drugIconImageView = dialog.findViewById(R.id.dialogDrugIconimageView) ;

        timeTextView.setText(currentTime.getHour()+":"+currentTime.getMinute());
        drugIconImageView.setImageResource(medicine.getImage());
        String decMed = ""+ medicine.getStrengthAmount() + medicine.getStrength() + "\n\n " +medicine.getForm();
        drugNameTextView.setText(medicine.getName());
        drugDescrTextView.setText(decMed);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CustomTime> doseTimes = medicine.getDoseTimes();
                for (CustomTime customTime:doseTimes) {
                    if(customTime.equals(currentTime)){
                        customTime.setStatus(Status.Skip);
                    }
                }
                medicine.setDoseTimes(doseTimes);
                //presenter code
                allMedViewPresenter.updateMedicine(medicine);
                allMedViewPresenter.updateUserWithNewMedData(medicine);

                allMedViewPresenter.changeDoseState(medicine,currentTime,Status.Skip);


                dialog.dismiss();
            }
        });




        takeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CustomTime> doseTimes = medicine.getDoseTimes();
                for (CustomTime customTime:doseTimes) {
                    if(customTime.equals(currentTime)){
                        customTime.setStatus(Status.Take);
                        Double pillsLeft = medicine.getNumberOfPillsLeft();

                        medicine.setNumberOfPillsLeft(pillsLeft - 1);
                        if (pillsLeft - 1 <= medicine.getRemindMeWhenIHaveHowManyPillsLeft())
                        {
                            displayNotification(String.valueOf(Integer.MAX_VALUE/2), "Refill Reminder", "Don't forget to refill your " + medicine.getName(), medicine.getImage());
                        }
                    }
                }
                medicine.setDoseTimes(doseTimes);

                allMedViewPresenter.updateMedicine(medicine);
                allMedViewPresenter.updateUserWithNewMedData(medicine);
                allMedViewPresenter.changeDoseState(medicine,currentTime,Status.Take);


                dialog.dismiss();
            }
        });




        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
                repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);


                SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                userEmail = preferences.getString("emailKey" , "user email") ;
                User currentUser = repoClass.findUserByEmail(userEmail);


                List<CustomTime> doseTimes = medicine.getDoseTimes();
                for(int i=0;i<doseTimes.size();i++){
                    if(doseTimes.get(i).equals(currentTime)){
                        doseTimes.get(i).setStatus(Status.Snooze);
                    }
                }


                //remove it when status snooze from work manager
                medicine.setDoseTimes(doseTimes);

                allMedViewPresenter.updateMedicine(medicine);
                allMedViewPresenter.updateUserWithNewMedData(medicine);
                allMedViewPresenter.changeDoseState(medicine,currentTime,Status.Snooze);


                dialog.dismiss();
            }

        });

        dialog.show();
    }


}
