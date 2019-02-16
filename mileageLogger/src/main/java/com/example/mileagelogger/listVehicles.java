package com.example.mileagelogger;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class listVehicles extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehlist);




        mileageDBHelper mdbh = new mileageDBHelper(this.getApplicationContext());
        final SQLiteDatabase db = mdbh.getWritableDatabase();


        int names[] = {android.R.id.text1, android.R.id.text2};
        final Cursor c = db.rawQuery("Select _id, vehName AS Vdata from vehicles  order by _id ASC", null);


        final ListAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, c,
                new String[]{"_id", "Vdata"},
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
                b.putInt("id", c1.getInt(c1.getColumnIndex("_id")));
                b.putString("vehName", c1.getString(c1.getColumnIndex("vehName")));


                Intent myIntent = new Intent(getBaseContext(), vehMaint.class);
                myIntent.putExtras(b);

                startActivityForResult(myIntent, 0);
                //Toast.makeText(getBaseContext(),"item" + id + " checked",Toast.LENGTH_SHORT).show();
            }
        });



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.addVehicle) {
            // launch vehicle maint class
            Intent myIntent = new Intent(getBaseContext(), vehMaint.class);
            startActivityForResult(myIntent, 0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
