/*
kv4p HT (see http://kv4p.com)
Copyright (C) 2024 Vance Vagell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.vagell.kv4pht.data.migrations;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class MigrationFrom5To6 extends Migration {
    public MigrationFrom5To6() {
        super(5, 6);
    }

    @Override
    public void migrate(SupportSQLiteDatabase database) {
        // We renamed and changed capitalization on a few setting values, adjust them (we did not change DB schema).
        database.execSQL("UPDATE app_settings SET value = 'Approx' WHERE name = 'aprsPositionAccuracy' AND value = 'approx'");
        database.execSQL("UPDATE app_settings SET value = 'Exact' WHERE name = 'aprsPositionAccuracy' AND value = 'exact'");
        database.execSQL("UPDATE app_settings SET value = '12.5kHz' WHERE name = 'bandwidth' AND value = 'Narrow'");
        database.execSQL("UPDATE app_settings SET value = '25kHz' WHERE name = 'bandwidth' AND value = 'Wide'");
		
		
				// ערוצי DUPLEX (תדר שידור שונה מתדר קליטה - Offset של 4.6MHz)
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH01 Port Ops', '160.6500', 'Marine VHF', 1, 'None', 'None', -4600, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH02 Public', '160.7000', 'Marine VHF', 1, 'None', 'None', -4600, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH03 Public', '160.7500', 'Marine VHF', 1, 'None', 'None', -4600, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH04 Port/VTS', '160.8000', 'Marine VHF', 1, 'None', 'None', -4600, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH05 VTS Control', '160.8500', 'Marine VHF', 1, 'None', 'None', -4600, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH07 General', '160.9500', 'Marine VHF', 1, 'None', 'None', -4600, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH18 Public', '161.5000', 'Marine VHF', 1, 'None', 'None', -4600, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH20 Port Ops', '161.6000', 'Marine VHF', 1, 'None', 'None', -4600, 0)");

		// ערוצי SIMPLEX (תדר שידור וקליטה זהים - Offset 0)
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH06 Inter-ship', '156.3000', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH08 Inter-ship', '156.4000', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH09 Marina', '156.4500', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH10 Working', '156.5000', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH11 VTS', '156.5500', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH12 Port Ops', '156.6000', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH13 Bridge-Bridge', '156.6500', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH14 Port Admin', '156.7000', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH15 Intra-ship', '156.7500', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH16 DISTRESS', '156.8000', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH17 Port Admin', '156.8500', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH67 Safety', '156.3750', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH68 Marina', '156.4250', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH69 Working', '156.4750', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH71 Port Ops', '156.5750', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH72 Inter-ship', '156.6250', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH73 Weather/SAR', '156.6750', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH74 Port Ops', '156.7250', 'Marine VHF', 0, 'None', 'None', 0, 0)");
		database.execSQL("INSERT INTO channel_memories (name, frequency, 'group', offset, tx_tone, rx_tone, offset_khz, skip_during_scan) VALUES ('CH77 Inter-ship', '156.8750', 'Marine VHF', 0, 'None', 'None', 0, 0)");
			}
}