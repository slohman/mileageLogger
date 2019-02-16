package com.example.mileagelogger;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int TripID = 0;
    final Calendar myCalendar = Calendar.getInstance();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);



        Bundle b = this.getIntent().getExtras();
        EditText endmiles = (EditText)findViewById(R.id.etEmiles);
        loadSpinners();
         TripID = fillForm(b);



            endmiles.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                // this should update the miles traveled when end Miles loses focus
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        // code to execute when EditText loses focus
                        EditText start = (EditText) findViewById(R.id.etSmiles);
                        EditText end = (EditText) findViewById(R.id.etEmiles);
                        TextView net = (TextView) findViewById(R.id.tvNetMiles);

                        int endMiles = Integer.parseInt(end.getText().toString());
                        int startMiles = Integer.parseInt(start.getText().toString());
                        int netmiles = endMiles - startMiles;
                        String sNetMiles = String.valueOf(netmiles);

                        net.setText(sNetMiles);

                    }
                }
            });

            DatePickerHelper assessmentDueDateHelper = new DatePickerHelper(MainActivity.this,
                    (TextView) findViewById(R.id.etDate));





    }
    private void loadSpinners(){

        Spinner veh = (Spinner) findViewById(R.id.spVehicles);
        ArrayAdapter<?> adapter1 = ArrayAdapter.createFromResource(
                this, R.array.vehicles, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        veh.setAdapter(adapter1);

        Spinner use = (Spinner) findViewById(R.id.spUse);
        ArrayAdapter<?> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.uses, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        use.setAdapter(adapter2);

        Spinner ftype = (Spinner) findViewById(R.id.spFtype);
        ArrayAdapter<?> adapter3 = ArrayAdapter.createFromResource(
                this, R.array.fuletyp, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ftype.setAdapter(adapter3);
    }

    public void setSpinText(Spinner spin, String text)
    {
        for(int i= 0; i < spin.getAdapter().getCount(); i++)
        {
            if(spin.getAdapter().getItem(i).toString().contains(text))
            {
                spin.setSelection(i);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.delteItem) {
            // delete selected item from list
            return true;
        }
        if (id == R.id.exportData) {


                    try {
                        util u = new util();
                        Context ctx = getApplicationContext();
                        String response = u.saveDB("mileageTrakker.db", ctx);
                        u.Showaction(response, ctx);


                    } catch (Exception e) {
                        String ex = e.toString();
                    }
            return true;
        }
        if (id == R.id.importDB) {

            try{
            util u = new util();
            Context ctx = getApplicationContext();
            String response = u.RestoreDB("mileageTrakker.db",ctx);
            u.Showaction(response,ctx);
            } catch (Exception e) {
                String ex = e.toString();
            }
            return true;
        }
        if (id == R.id.saveRecord){

            // save record to sqlite db here !
            String typ = "New";
            saveToDB(typ,TripID);
            clearScreen();
        }
        if (id == R.id.listDB){
            Intent myIntent = new Intent(getBaseContext(), listTrips.class);
            startActivityForResult(myIntent, 0);
        }
        if (id == R.id.listVEH){
            Intent myIntent = new Intent(getBaseContext(), listVehicles.class);
            startActivityForResult(myIntent, 0);
        }

        return super.onOptionsItemSelected(item);

    }

    private void saveToDB(String typ, int TripID) {
        SQLiteDatabase database = new mileageDBHelper(this).getReadableDatabase();
        // get values from form
        EditText dte = (EditText) findViewById(R.id.etDate);
        EditText start = (EditText) findViewById(R.id.etSmiles);
        EditText end = (EditText) findViewById(R.id.etEmiles);
        TextView net = (TextView) findViewById(R.id.tvNetMiles);
        Spinner veh = (Spinner) findViewById(R.id.spVehicles);
        Spinner use = (Spinner) findViewById(R.id.spUse);
        Spinner ftype = (Spinner) findViewById(R.id.spFtype);
        EditText fqty = (EditText) findViewById(R.id.etFqty);
        EditText fcost= (EditText) findViewById(R.id.etFcost);
        TextView notes = (TextView) findViewById(R.id.etNotes);

        String travelDate = dte.getText().toString();
        int startMileage = Integer.parseInt(start.getText().toString());
        int endMileage = Integer.parseInt(end.getText().toString());
        int netMileage = Integer.parseInt(net.getText().toString());
        int intSpinnerPosVeh = veh.getSelectedItemPosition();

        String vehicle = veh.getItemAtPosition(intSpinnerPosVeh).toString();
        int intSpinnerPosUse = veh.getSelectedItemPosition();
        String usetype = use.getItemAtPosition(intSpinnerPosUse).toString();
        String fueltype =  ftype.getItemAtPosition(intSpinnerPosUse).toString();
        String fuelCost = String.format(fcost.getText().toString());
        String fuelQty = String.format(fqty.getText().toString());
        float fuelcost = Float.parseFloat(fuelCost);
        float fuelqty = Float.parseFloat(fuelQty);
        String comm = notes.getText().toString();


        ContentValues values = new ContentValues();

        values.put(LoggerDBManager.Mileage.COLUMN_DATE, travelDate);
        values.put(LoggerDBManager.Mileage.COLUMN_SMILES , startMileage);
        values.put(LoggerDBManager.Mileage.COLUMN_EMILES , endMileage);
        values.put(LoggerDBManager.Mileage.COLUMN_NMILES , netMileage);
        values.put(LoggerDBManager.Mileage.COLUMN_VEHICLE, vehicle);
        values.put(LoggerDBManager.Mileage.COLUMN_TYPE, usetype);
        values.put(LoggerDBManager.Mileage.COLUMN_NOTES, comm);
        values.put(LoggerDBManager.Mileage.COLUMN_FUEL, fueltype);
        values.put(LoggerDBManager.Mileage.COLUMN_FUELQTY, fuelqty);
        values.put(LoggerDBManager.Mileage.COLUMN_FUELCOST,fuelcost);

        if (TripID == 0) {
            long newRowId = database.insert(LoggerDBManager.Mileage.TABLE_NAME, null, values);
            Toast.makeText(this, "The new Row Id is " + newRowId, Toast.LENGTH_LONG).show();
            database.close();
        }else{


            long rowUpdated = database.update(LoggerDBManager.Mileage.TABLE_NAME, values, "_id = " + TripID,null );
            Toast.makeText(this, " Updated Row " + rowUpdated, Toast.LENGTH_LONG).show();
            database.close();
        }

    }
    public void clearScreen(){

        EditText dte = (EditText) findViewById(R.id.etDate);
        EditText start = (EditText) findViewById(R.id.etSmiles);
        EditText end = (EditText) findViewById(R.id.etEmiles);
        TextView net = (TextView) findViewById(R.id.tvNetMiles);
        Spinner veh = (Spinner) findViewById(R.id.spVehicles);
        Spinner use = (Spinner) findViewById(R.id.spUse);
        TextView notes = (TextView) findViewById(R.id.etNotes);
        Spinner ftype = (Spinner) findViewById(R.id.spFtype);
        EditText fuelcost = (EditText) findViewById(R.id.etFcost);
        EditText fqty = (EditText) findViewById(R.id.etFqty);

        dte.setText((""));
        start.setText("");
        end.setText("");
        net.setText("");
        notes.setText("");
        fuelcost.setText("");
        fqty.setText("");
        ftype.setTag("");

    }
    public void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        EditText dte = (EditText) findViewById(R.id.etDate);
        dte.setText(sdf.format(myCalendar.getTime()));
    }
    private void calcmiles(){

        // calculate miles driven

        EditText start = (EditText) findViewById(R.id.etSmiles);
        EditText end = (EditText) findViewById(R.id.etEmiles);
        TextView net = (TextView) findViewById(R.id.tvNetMiles);

        int endMiles = Integer.parseInt(end.getText().toString());
        int startMiles = Integer.parseInt(start.getText().toString());
        int netmiles = endMiles - startMiles;
        String sNetMiles = String.valueOf(netmiles);

        net.setText(sNetMiles);


    }
    public int fillForm(Bundle b){


        if (b != null){ // loaded this from the list for an update
            // ge the data and populate the form for an Update

            TripID = b.getInt("trip");
            String tripDate = b.getString("tripDate");
            String startMiles = b.getString("start");
            String endMiles = b.getString("end");
            String netMiles = b.getString("net");
            String vehicle = b.getString("veh");
            String useType = b.getString("usetype");
            String fuelType = b.getString("fueltype");
            String fuelqty = b.getString("fuelqty");
            String fuelcost = b.getString("fuelcost");
            String tripNotes= b.getString("notes");

            EditText dte = (EditText) findViewById(R.id.etDate);
            dte.setText(tripDate);
            EditText start = (EditText) findViewById(R.id.etSmiles);
            start.setText(String.valueOf(startMiles));
            EditText end = (EditText) findViewById(R.id.etEmiles);
            end.setText(String.valueOf(endMiles));
            TextView net = (TextView) findViewById(R.id.tvNetMiles);
            net.setText(String.valueOf(netMiles));

            Spinner veh = (Spinner) findViewById(R.id.spVehicles);
            String[] vehs = getResources().getStringArray(R.array.vehicles);
            SetSpinnerSelection(veh,vehs,vehicle);



            Spinner use = (Spinner) findViewById(R.id.spUse);
            String[] uses= getResources().getStringArray(R.array.uses);
            SetSpinnerSelection(use,uses,useType);


            Spinner ftype = (Spinner) findViewById(R.id.spUse);
            String[] fuels = getResources().getStringArray(R.array.fuletyp);
            SetSpinnerSelection(ftype,fuels,fuelType);


            EditText fqty = (EditText) findViewById(R.id.etFqty);
            fqty.setText(String.valueOf(fuelqty));
            EditText fcost= (EditText) findViewById(R.id.etFcost);
            fcost.setText(String.valueOf(fuelcost));
            TextView notes = (TextView) findViewById(R.id.etNotes);
            notes.setText(tripNotes);




            return TripID;
        }else{
            int tripID = 0;
            return tripID;
        }

    }

    public void SetSpinnerSelection(Spinner spinner,String[] array,String text) {
        for(int i=0;i<array.length;i++) {
            if(array[i].equals(text)) {
                spinner.setSelection(i);
            }
        }
    }

}

