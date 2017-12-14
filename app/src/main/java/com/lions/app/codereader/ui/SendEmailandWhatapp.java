package com.lions.app.codereader.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lions.app.codereader.DB.DBHelper;
import com.lions.app.codereader.DB.GPSCoordinates;
import com.lions.app.codereader.DB.SingleShotLocationProvider;
import com.lions.app.codereader.R;

import java.util.HashMap;

public class SendEmailandWhatapp extends AppCompatActivity {

    String code;
    HashMap<String,String> map = new HashMap<String, String>();
    DBHelper dbHelper;
    Button sendemail, sendwhatsapp;
    TextView details;
    private double latitude;
    private double longitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_emailand_whatapp);
        Intent intent = getIntent();

        dbHelper = new DBHelper(getApplicationContext());
        code = intent.getStringExtra("code");
        map = dbHelper.GetDetailsbyCode(code);
        foo(getApplicationContext());
        sendemail = (Button)findViewById(R.id.sendemail);
        sendwhatsapp = (Button)findViewById(R.id.sendhwhatsapp);
        details = (TextView)findViewById(R.id.details);

        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto",map.get("email"), null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "My location and text");
                intent.putExtra(Intent.EXTRA_TEXT, "This is demo text");
                intent.putExtra(Intent.EXTRA_TEXT,whatsAppMessage);
                intent.setType("text/plain");
                startActivity(intent);
                */

                String whatsAppMessage = "http://maps.google.com/maps/api/staticmap?center="+latitude+","+longitude+"&zoom=13&size=500x300&sensor=TRUE_OR_FALSE "+" \n This is my location ";

                ShareCompat.IntentBuilder.from(SendEmailandWhatapp.this)
                        .setType("message/rfc822")
                        .addEmailTo(map.get("email"))
                        .setSubject("Demo Subject Here")
                        .setText(whatsAppMessage)
                        //.setHtmlText(whatsAppMessage) //If you are using HTML in your body text
                        .setChooserTitle("Choose your client")
                        .startChooser();

            }
        });

        sendwhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Uri uri = Uri.parse("smsto:" + map.get("phone"));
                Intent sendIntent = new Intent("android.intent.action.MAIN");
                // sendIntent.setComponent(new  ComponentName("com.whatsapp","com.whatsapp.Conversation"));
                String n =  PhoneNumberUtils.stripSeparators(map.get("phone"))+"@s.whatsapp.net".replace("+","");

                sendIntent.putExtra("jid", n);
                Log.d("number",""+n);
                //phone number without "+" prefix
                //sendIntent.putExtra(Intent.EXTRA_SUBJECT, "This is a demo text");
                String whatsAppMessage = "http://maps.google.com/maps/api/staticmap?center="+latitude+","+longitude+"&zoom=13&size=500x300&sensor=TRUE_OR_FALSE"+" \n This is my location";
                sendIntent.putExtra(Intent.EXTRA_TEXT, whatsAppMessage);
                sendIntent.setType("text/html");
                sendIntent.setPackage("com.whatsapp");
                sendIntent.setAction(Intent.ACTION_SEND);
                startActivity(sendIntent);
            }
        });

        if(map!=null)
        {
            details.setText("Name : "+map.get("name")+"\n Email "+map.get("email")+"\n Phone "+map.get("phone"));
        }
        else
        {
            Toast.makeText(getApplicationContext(),"No user with this code",Toast.LENGTH_SHORT).show();
        }
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
