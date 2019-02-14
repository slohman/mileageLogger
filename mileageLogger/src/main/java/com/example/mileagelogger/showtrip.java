package com.example.mileagelogger;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class showtrip extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showtrip);
      //  Toolbar toolbar = findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);

        Bundle b = this.getIntent().getExtras();

        int tripNo = b.getInt("trip");
        String tripDate = b.getString("tripDate");
        int startMiles = b.getInt("start");
        int endMiles = b.getInt("end");
        int netMiles = b.getInt("net");
        String vehicle = b.getString("veh");
        String useType = b.getString("usetype");
        String fuelType = b.getString("fueltype");
        float fqty = b.getFloat("fuelqty");
        float fcost = b.getFloat("fuelcost");
        String tripNotes= b.getString("notes");

       TextView tv = (TextView)findViewById(R.id.tripdetail);
       String tripinfo = "Trip Details for Trip # " + tripNo + "\n";
       tripinfo = tripinfo +  "Trip Date: "+ tripDate + "\n" ;
        tripinfo = tripinfo +  "Start Miles: "+ startMiles+ "\n" ;
        tripinfo = tripinfo +  "End Miles: "+ endMiles + "\n" ;
        tripinfo = tripinfo +  "Net Miles: "+ netMiles + "\n" ;
        tripinfo = tripinfo +  "Vehicle: "+ vehicle+ "\n" ;
        tripinfo = tripinfo +  "Use Type: "+ useType + "\n" ;
        tripinfo = tripinfo +  "Fuel Purch: "+ fuelType + "\n" ;
        tripinfo = tripinfo +  "Fuel Qty: "+fqty+ "\n" ;
        tripinfo = tripinfo +  "Fuel Cost: "+ fcost + "\n" ;
        tripinfo = tripinfo +  "Notes: "+ tripNotes + "\n" ;








       tv.setText(tripinfo);


    }

}
