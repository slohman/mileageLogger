package com.example.mileagelogger;

import android.provider.BaseColumns;

public class LoggerDBManager {

    public LoggerDBManager() {
    }

    public static class Mileage implements BaseColumns {
        public static final String TABLE_NAME = "miles";
        public static final String COLUMN_DATE = "dte"; // date is a reserved SQlite word !!!!
        public static final String COLUMN_SMILES = "smiles";
        public static final String COLUMN_EMILES= "emiles";
        public static final String COLUMN_NMILES= "nmiles";
        public static final String COLUMN_VEHICLE =  "vehicle";
        public static final String COLUMN_TYPE= "type";
        public static final String COLUMN_NOTES= "notes";
        public static final String COLUMN_FUEL = "fuel";
        public static final String COLUMN_FUELQTY = "fuelQTY";
        public static final String COLUMN_FUELCOST = "fuelcost";
        public static final String COLUMN_VEHNAME = "vehName";
        public static final String COLUMN_USETYPE = "useType";
        public static final String COLUMN_FUELTYPE = "fuelType";
        public static final String COLUMN_VEHTYPE = "vehType";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_SMILES + " INTEGER, " +
                COLUMN_EMILES + " INTEGER, " +
                COLUMN_NMILES + " INTEGER, " +
                COLUMN_VEHICLE + " TEXT, " +
                COLUMN_TYPE + " TEXT, " +
                COLUMN_FUEL + " TEXT, " +
                COLUMN_FUELQTY + " REAL," +
                COLUMN_FUELCOST + " REAL," +
                COLUMN_NOTES+ " TEXT" + ")";

        public static final String CREATE_TABLEFUEL = "CREATE TABLE IF NOT EXISTS " +
                "fuelTPYES" + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FUELTYPE + " TEXT " + ")";


        public static final String CREATE_TABLEUSES = "CREATE TABLE IF NOT EXISTS " +
                "uses" + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USETYPE + " TEXT " + ")";

        public static final String CREATE_TABLEVEH = "CREATE TABLE IF NOT EXISTS " +
                "vehicles" + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VEHNAME + " TEXT, " +
                COLUMN_VEHTYPE + " TEXT " + ")";

 }



}
