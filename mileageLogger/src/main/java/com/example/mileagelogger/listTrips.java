package com.example.mileagelogger;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class listTrips extends AppCompatActivity {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.triplist);




       mileageDBHelper mdbh = new mileageDBHelper(this.getApplicationContext());
        final SQLiteDatabase db = mdbh.getWritableDatabase();


        int names[] = {android.R.id.text1, android.R.id.text2};
        final Cursor c = db.rawQuery("Select _id, dte, vehicle||' '|| nmiles ||' ' || type AS Fdata from miles  order by dte ASC", null);


        final ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c,
                new String[]{"dte", "Fdata"},
                names);

        startManagingCursor(c);


        ListView lv = findViewById(R.id.andlistview);
        lv.setAdapter(adapter);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                final Cursor c1 = db.rawQuery("Select * from miles  where _id = "+ id, null);
                // Display a messagebox.
                Bundle b = new Bundle();
                c1.moveToPosition(position);
                b.putInt("trip", c1.getInt(c1.getColumnIndex("_id")));
                b.putString("tripDate", c1.getString(c1.getColumnIndex("dte")));
                b.putInt("start", c1.getInt(c1.getColumnIndex("smiles")));
                b.putInt("end", c1.getInt(c1.getColumnIndex("emiles")));
                b.putInt("net", c1.getInt(c1.getColumnIndex("nmiles")));
                b.putString("veh", c1.getString(c1.getColumnIndex("vehicle")));
                b.putString("usetype", c1.getString(c1.getColumnIndex("type")));
                b.putString("fueltype", c1.getString(c1.getColumnIndex("fuel")));
                b.putString("fuelqty", c1.getString(c1.getColumnIndex("fuelQTY")));
                b.putString("fuelcost", c1.getString(c1.getColumnIndex("fuelcost")));
                b.putString("notes", c1.getString(c1.getColumnIndex("notes")));

                Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
                myIntent.putExtras(b);

                startActivityForResult(myIntent, 0);
                //Toast.makeText(getBaseContext(),"item" + id + " checked",Toast.LENGTH_SHORT).show();
            }
        });


    }



}
