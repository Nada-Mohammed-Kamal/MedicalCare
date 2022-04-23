package com.example.medicalappreminder_java.HomeScreen.View.HomeFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.sectionedrecyclerview.SectionedRecyclerViewAdapter;
import com.example.medicalappreminder_java.Constants.Status;
import com.example.medicalappreminder_java.R;
import com.example.medicalappreminder_java.Repo.RepoClass;
import com.example.medicalappreminder_java.Repo.local.ConcreteLocalSource;
import com.example.medicalappreminder_java.Repo.local.LocalSourceInterface;
import com.example.medicalappreminder_java.Repo.remote.FireStoreHandler;
import com.example.medicalappreminder_java.Repo.remote.RemoteSourceInterface;
import com.example.medicalappreminder_java.models.CustomTime;
import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;
import com.example.medicalappreminder_java.roomdb.UserData;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


//AllMoviesAdapter
public class AllMedAdapter extends SectionedRecyclerViewAdapter<AllMedAdapter.ViewHolder> {

    private final Context context;
    private ArrayList<Medicine> medList;
    OnMoviesClickListener onMoviesClickListener;
    List<CustomTime> todayesTimesOfDoses;
    private List<Medicine> sectionMedicines;
    private List<List<Medicine>> allSectionsMedicines;
    Dialog dialog ;
    public AllMedAdapter(Context context, ArrayList<Medicine> values, List<CustomTime>  todayesTimesOfDoses , OnMoviesClickListener onMoviesClickListener) {
        this.context = context;
        this.medList = values;
        this.onMoviesClickListener = onMoviesClickListener;
        dialog = new Dialog(context);
        this.todayesTimesOfDoses =  todayesTimesOfDoses;
        sectionMedicines = new ArrayList<>();
        allSectionsMedicines = new ArrayList<>();
    }

    @Override
    public int getSectionCount() {
        return todayesTimesOfDoses.size();
    }

    @Override
    public int getItemCount(int section) {
        sectionMedicines = UserData.getSectionMedicines(medList, todayesTimesOfDoses.get(section));
        allSectionsMedicines.add(sectionMedicines);
        return sectionMedicines.size();
    }

    @Override
    public void onBindHeaderViewHolder(AllMedAdapter.ViewHolder holder, int section) {
        holder.medTime.setText(todayesTimesOfDoses.get(section).getHour()+":"+todayesTimesOfDoses.get(section).getMinute());

    }

    @Override
    public void onBindViewHolder(AllMedAdapter.ViewHolder holder, int section, int relativePosition, int absoultePosition) {
        sectionMedicines = allSectionsMedicines.get(section);
        Medicine medDTO = sectionMedicines.get(relativePosition);
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

        Button skipButton , takeButton , snoozeButton ;
        TextView timeTextView , drugNameTextView  ,drugDescrTextView ;
        ImageView drugIconImageView ;

        skipButton = dialog.findViewById(R.id.dialogSkipButton) ;
        takeButton = dialog.findViewById(R.id.dialogTakeButton);
        snoozeButton = dialog.findViewById(R.id.dialogSnoozeButton) ;
        timeTextView = dialog.findViewById(R.id.dialogTimeTextView) ;
        drugNameTextView = dialog.findViewById(R.id.dialogDrugNameTextView) ;
        drugDescrTextView = dialog.findViewById(R.id.dialogDrugDescrTextView) ;
        drugIconImageView = dialog.findViewById(R.id.dialogDrugIconimageView) ;

        timeTextView.setText(currentTime.getHour()+":"+currentTime.getMinute());
        drugIconImageView.setImageResource(medicine.getImage());
        String decMed = ""+ medicine.getStrengthAmount() + medicine.getStrength() + ",take " +medicine.getForm();
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
                RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
                RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
                repoClass.updateMedicine(medicine);
                SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                String userEmail = preferences.getString("emailKey" , "user email") ;
                User currentUser = repoClass.findUserByEmail(userEmail);
                List<Medicine> listOfMedications = currentUser.getListOfMedications();
                for (Medicine oldMed:listOfMedications) {
                    if(oldMed.getUuid().equals(medicine.getUuid())){
                        listOfMedications.remove(oldMed);
                        break;
                    }
                }
                listOfMedications.add(medicine);
                currentUser.setListOfMedications(listOfMedications);
                repoClass.updateUser(currentUser);
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
                    }
                }
                medicine.setDoseTimes(doseTimes);
                //presenter code
                RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
                RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
                repoClass.updateMedicine(medicine);
                SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                String userEmail = preferences.getString("emailKey" , "user email") ;
                User currentUser = repoClass.findUserByEmail(userEmail);
                List<Medicine> listOfMedications = currentUser.getListOfMedications();
                for (Medicine oldMed:listOfMedications) {
                    if(oldMed.getUuid().equals(medicine.getUuid())){
                        listOfMedications.remove(oldMed);
                        break;
                    }
                }
                listOfMedications.add(medicine);
                currentUser.setListOfMedications(listOfMedications);
                repoClass.updateUser(currentUser);
                dialog.dismiss();
            }
        });
        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<CustomTime> doseTimes = medicine.getDoseTimes();
                for(int i=0;i<doseTimes.size();i++){
                    if(doseTimes.get(i).equals(currentTime)){
                        doseTimes.get(i).setStatus(Status.Snooze);
                        CustomTime newCustomTime = new CustomTime(doseTimes.get(i).getHour(),doseTimes.get(i).getMinute()+10);
                        doseTimes.add(i+1,newCustomTime);
                    }
                }
                //remove it when status snooze from work manager
                medicine.setDoseTimes(doseTimes);
                //presenter code
                RemoteSourceInterface remoteSourceInterface = new FireStoreHandler();
                LocalSourceInterface localSourceInterface = new ConcreteLocalSource(context);
                RepoClass repoClass = RepoClass.getInstance(remoteSourceInterface,localSourceInterface,context);
                repoClass.updateMedicine(medicine);
                SharedPreferences preferences = context.getSharedPreferences("preferencesFile" , Context.MODE_PRIVATE) ;
                String userEmail = preferences.getString("emailKey" , "user email") ;
                User currentUser = repoClass.findUserByEmail(userEmail);
                List<Medicine> listOfMedications = currentUser.getListOfMedications();
                for (Medicine oldMed:listOfMedications) {
                    if(oldMed.getUuid().equals(medicine.getUuid())){
                        listOfMedications.remove(oldMed);
                        break;
                    }
                }
                listOfMedications.add(medicine);
                currentUser.setListOfMedications(listOfMedications);
                repoClass.updateUser(currentUser);
                dialog.dismiss();
            }

        });
        dialog.show();
    }
}

