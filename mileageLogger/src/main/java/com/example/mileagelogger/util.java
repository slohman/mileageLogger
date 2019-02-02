package com.example.mileagelogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;


public class util  {





    public void Showaction(String msg, Context ctx) {

        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(ctx, msg, duration);
        toast.show();
    }

    public String saveDB(String dbase, Context ctx) {
        File sd = new File(Environment.getExternalStorageDirectory().toString() + "/mileageTrakkerData/");

        String response = "";


        try {
            if (sd.canWrite() == true) {
                //do Nothing
            } else {
                sd.mkdirs();
            }

            mileageDBHelper mdbh = new mileageDBHelper(ctx);
            final SQLiteDatabase db = mdbh.getWritableDatabase();


            // File currentDB = new File(ctx.getDatabasePath(dbase).toString() + dbase);
            //File CurDB = new File (ctx.getDatabasePath(dbase).toString());


            // String curDB = DataDir.toString();
            final File DataDir = new File("/data/user/0/com.sglent.mileagelogger/databases/mileageTrakker.db");
            File backupDB = new File(sd, dbase);
            FileChannel src = new FileInputStream(DataDir).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();

            dst.transferFrom(src, 0, src.size());
            response = dbase + " Saved to " + backupDB.toString();

            src.close();
            dst.close();


        } catch (IOException e) {

            response = dbase + " Save Failed";
        }


        return response;

    }
    public String RestoreDB(String dbase, Context ctx) {
        File sd = new File(Environment.getDataDirectory().toString()+"/databases/");
        String ExtDir = (Environment.getExternalStorageDirectory().toString() + "/mileageTrakkerData/");
        String response = "";
        try {



            File restoreToDB = new File(ctx.getDatabasePath(dbase).toString());
            File backupDB = new File(ExtDir, dbase);
            FileChannel src = new FileInputStream(backupDB).getChannel();
            FileChannel dst = new FileOutputStream(restoreToDB).getChannel();

            dst.transferFrom(src, 0, src.size());
            response = dbase + " Restored from " + backupDB.toString();

            src.close();
            dst.close();


        } catch(IOException e){

            response = dbase + " Restore Failed";
        }


        return response;

    }


}
