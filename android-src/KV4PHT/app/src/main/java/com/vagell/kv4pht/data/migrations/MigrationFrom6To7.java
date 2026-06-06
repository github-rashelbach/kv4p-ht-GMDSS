/*
kv4p HT (see http://kv4p.com)
Copyright (C) 2024 Vance Vagell
... (license header)
*/

package com.vagell.kv4pht.data.migrations;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class MigrationFrom6To7 extends Migration {
    public MigrationFrom6To7() {
        super(6, 7);
    }

    @Override
    public void migrate(SupportSQLiteDatabase database) {
        // No schema changes between version 6 and 7.
        // Default Marine VHF channels are now seeded cleanly in DatabaseSeeder.onCreate().
        // This migration exists only to maintain the migration chain.
    }
}