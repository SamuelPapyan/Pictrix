package com.example.pictrix.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {
        Images.class,
        Videos.class
        },
        version = 2
)
public abstract class AppDatabase extends RoomDatabase{

    private static final String FDB_Name = "pictrix_db";
    private static volatile AppDatabase INSTANCE;

    static Migration migration = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };

    public abstract ImageDao getImageDao();
    public abstract VideoDao getVideoDao();

    public static synchronized AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            Builder<AppDatabase> appDatabaseBuilder = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    FDB_Name
            );
            INSTANCE = appDatabaseBuilder.allowMainThreadQueries().build();
            return INSTANCE;
        }
        else
            return INSTANCE;
    }
}
