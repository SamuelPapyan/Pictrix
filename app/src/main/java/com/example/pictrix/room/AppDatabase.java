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
        Videos.class,
        Comments.class
        },
        version = 4
)
public abstract class AppDatabase extends RoomDatabase{

    private static final String FDB_Name = "pictrix_db";
    private static volatile AppDatabase INSTANCE;

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("alter table images add column littleImageUrl varchar(50)");
        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3,4){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("create table comments(id INTEGER primary key not null, postId INTEGER not null, contentText TEXT);");
        }
    };

    public abstract ImageDao getImageDao();
    public abstract VideoDao getVideoDao();
    public abstract CommentDao getCommentDao();

    public static synchronized AppDatabase getInstance(Context context){
        if(INSTANCE == null){
            Builder<AppDatabase> appDatabaseBuilder = Room.databaseBuilder(
                    context,
                    AppDatabase.class,
                    FDB_Name
            );
            INSTANCE = appDatabaseBuilder
                    .allowMainThreadQueries()
                    .addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                    .build();
            return INSTANCE;
        }
        else
            return INSTANCE;
    }
}
