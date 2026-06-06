/*
kv4p HT (see http://kv4p.com)
Copyright (C) 2024 Vance Vagell
... (license header remains the same)
*/

package com.vagell.kv4pht.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.vagell.kv4pht.data.migrations.*;

/**
 * Singleton Room database for kv4p HT application.
 */
@Database(
    version = 8,
    entities = {AppSetting.class, ChannelMemory.class, APRSMessage.class}
)
@SuppressWarnings("java:S6548")
public abstract class AppDatabase extends RoomDatabase {

    public abstract AppSettingDao appSettingDao();
    public abstract ChannelMemoryDao channelMemoryDao();
    public abstract APRSMessageDao aprsMessageDao();

    // Migrations
    public static final Migration MIGRATION_1_2 = new MigrationFrom1To2();
    public static final Migration MIGRATION_2_3 = new MigrationFrom2To3();
    public static final Migration MIGRATION_3_4 = new MigrationFrom3To4();
    public static final Migration MIGRATION_4_5 = new MigrationFrom4To5();
    public static final Migration MIGRATION_5_6 = new MigrationFrom5To6();
    public static final Migration MIGRATION_6_7 = new MigrationFrom6To7();
    public static final Migration MIGRATION_7_8 = new MigrationFrom7To8();

    @SuppressWarnings({"java:S3077", "java:S3008"})
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "kv4pht-db")
            .addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3,
                MIGRATION_3_4,
                MIGRATION_4_5,
                MIGRATION_5_6,
                MIGRATION_6_7
            )
            .addCallback(new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    DatabaseSeeder.seedDefaultMarineChannels(db);
                }
            })
            // .fallbackToDestructiveMigration()  // ← ONLY for debugging! Remove in production
            .build();
    }

    // ... rest of your methods (saveAppSetting etc.)
}