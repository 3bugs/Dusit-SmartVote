package com.example.smartvote.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.smartvote.model.Party;

import java.util.ArrayList;
import java.util.List;

public class MyDb {

    private static final String DATABASE_NAME = "smart_vote";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PARTY = "party";
    private static final String COL_ID = "_id";
    private static final String COL_PARTY_NAME = "party_name";
    private static final String COL_LOGO = "logo";

    //เทเบิล party
    //+-----+------------+------+
    //| _id | party_name | logo |
    //+-----+------------+------+
    //|     |            |      |

    // CREATE TABLE party (_id INTEGER PRIMARY KEY AUTOINCREMENT, party_name TEXT, logo TEXT)

    private static final String CREATE_TABLE_PARTY = "CREATE TABLE " + TABLE_PARTY + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_PARTY_NAME + " TEXT, "
            + COL_LOGO + " TEXT "
            + ")";

    private static DbHelper sDbHelper;
    private SQLiteDatabase mDatabase;
    private Context mContext;

    public MyDb(Context context) {
        this.mContext = context.getApplicationContext();

        if (sDbHelper == null) {
            sDbHelper = new DbHelper(context);
        }
        mDatabase = sDbHelper.getWritableDatabase();
    }

    public List<Party> getAllParties() {
        List<Party> parties = new ArrayList<>();

        Cursor c = mDatabase.query(TABLE_PARTY, null, null, null, null, null, null);
        while (c.moveToNext()) {
            int id = c.getInt(c.getColumnIndex(COL_ID));
            String partyName = c.getString(c.getColumnIndex(COL_PARTY_NAME));
            String logo = c.getString(c.getColumnIndex(COL_LOGO));

            Party party = new Party(id, partyName, logo);
            parties.add(party);
        }

        return parties;
    }

    public void addParty(Party party) {
        ContentValues cv = new ContentValues();
        cv.put(COL_PARTY_NAME, party.partyName);
        cv.put(COL_LOGO, party.logo);
        mDatabase.insert(TABLE_PARTY, null, cv);
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE_PARTY);
            insertInitialData(db);
        }

        private void insertInitialData(SQLiteDatabase db) {
            ContentValues cv = new ContentValues();
            cv.put(COL_PARTY_NAME, "ประชาธิปัตย์");
            cv.put(COL_LOGO, "1.gif");
            db.insert(TABLE_PARTY, null, cv);

            cv = new ContentValues();
            cv.put(COL_PARTY_NAME, "พลังประชารัฐ");
            cv.put(COL_LOGO, "83.png");
            db.insert(TABLE_PARTY, null, cv);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    } // ปิดคลาส DbHelper
} // ปิดคลาส MyDb
