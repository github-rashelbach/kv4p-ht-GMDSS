/*
kv4p HT (see http://kv4p.com)
Copyright (C) 2024 Vance Vagell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by the
Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.vagell.kv4pht.data;

import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Responsible for seeding default data (e.g. Marine VHF channels).
 * Separated from migrations for better maintainability.
 */
public class DatabaseSeeder {

    public static void seedDefaultMarineChannels(SupportSQLiteDatabase db) {
        // All inserts use INSERT OR IGNORE to safely handle existing user data
        // and avoid duplicate key / constraint violations.

        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH06 Inter-ship', '156.3000', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH08 Inter-ship', '156.4000', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH09 Marina', '156.4500', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH10 Working', '156.5000', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH11 VTS', '156.5500', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH12 Port Ops', '156.6000', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH13 Bridge-Bridge', '156.6500', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH14 Port Admin', '156.7000', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH15 Intra-ship', '156.7500', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH16 DISTRESS', '156.8000', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH17 Port Admin', '156.8500', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH67 Safety', '156.3750', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH68 Marina', '156.4250', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH69 Working', '156.4750', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH71 Port Ops', '156.5750', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH72 Inter-ship', '156.6250', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH73 Weather/SAR', '156.6750', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH74 Port Ops', '156.7250', 'Marine VHF', 0, 'None', 'None', 0, 0);");
        db.execSQL("INSERT OR IGNORE INTO channel_memories (name, frequency, `group`, offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH77 Inter-ship', '156.8750', 'Marine VHF', 0, 'None', 'None', 0, 0);");
    }
}