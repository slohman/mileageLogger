package com.example.mileagelogger;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class mileageDBHelper extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 2;
        public static final String DATABASE_NAME = "mileageTrakker.db";

        public mileageDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL(LoggerDBManager.Mileage.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LoggerDBManager.Mileage.TABLE_NAME);
            onCreate(sqLiteDatabase);






            }
        }




