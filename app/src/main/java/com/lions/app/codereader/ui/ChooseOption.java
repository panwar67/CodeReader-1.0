package com.lions.app.codereader.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lions.app.codereader.DB.DBHelper;
import com.lions.app.codereader.DB.GPSCoordinates;
import com.lions.app.codereader.DB.SingleShotLocationProvider;
import com.lions.app.codereader.R;

public class ChooseOption extends AppCompatActivity {

    Button scan_qr, scan_bar;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_option);
        DBHelper dbhelper = new DBHelper(getApplicationContext());


        dbhelper.InitFilter();
        dbhelper.InsertDetails("Shubham Panwar","shubhampanwar67@yahoo.com","919871485365","1234");

        dbhelper.InsertDetails("Shubham Panwar","shubhampanwar67@yahoo.com","919871485365","9871");

        dbhelper.InsertDetails("Shubham Panwar","shubhampanwar67@yahoo.com","919871485365","5678");

        dbhelper.InsertDetails("Shubham Panwar","shubhampanwar67@yahoo.com","919871485365","4321");
        scan_qr = (Button)findViewById(R.id.scanqr);
        scan_bar = (Button)findViewById(R.id.scanbar);
        scan_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ChooseOption.this,QRCodeReaderActivity.class));
                finish();

            }
        });

        scan_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChooseOption.this,BarCodeReaderActivity.class));
                finish();
            }
        });

    }


    public void foo(Context context) {
        // when you need location
        // if inside activity context = this;

        SingleShotLocationProvider.requestSingleUpdate(context,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(GPSCoordinates location) {

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        Log.d("location",""+location.toString());

                    }


                });
    }

}
