package com.example.medicalappreminder_java.roomdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.medicalappreminder_java.models.Medicine;
import com.example.medicalappreminder_java.models.User;

@Database(entities = {User.class , Medicine.class}, version = 1 , exportSchema = true)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    //vars
    public abstract UserDao userDao();
    public abstract MedicineDao medicineDao();
    private static AppDatabase INSTANCE;


    //singleton(getInstanceFromDb)
    public static synchronized AppDatabase getDBInstance(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "myDBName")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}

//usage
//List<User> users = db.userDao().getAll(); ///masalan
