package com.example.hwandroid11;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
abstract class AppDatabase extends RoomDatabase
{
    abstract StudentDao studentDao();

    private static volatile AppDatabase INSTANCE;
    static AppDatabase getDatabase(final android.content.Context context)
    {
        if (INSTANCE == null)
        {
            synchronized (AppDatabase.class)
            {
                if (INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "student_database").build();
                }
            }
        }
        return INSTANCE;
    }
}