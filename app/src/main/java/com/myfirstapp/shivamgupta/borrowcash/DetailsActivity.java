package com.myfirstapp.shivamgupta.borrowcash;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by the master mind Mr.Shivam Gupta on 2/3/2016.
 */
public class DetailsActivity extends ActionBarActivity{
    SharedPreferences sp;
    Button paynow;
    TextView nname,nnumber,aadd,eemail,aamount,ttime,ttm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.ttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Account");

        nname = (TextView)findViewById(R.id.dname);
        nnumber = (TextView)findViewById(R.id.dphone);
        aadd = (TextView)findViewById(R.id.daddress);
        eemail = (TextView)findViewById(R.id.dmail);
        aamount = (TextView)findViewById(R.id.damount);
        ttime = (TextView)findViewById(R.id.dtime);
        ttm = (TextView)findViewById(R.id.dramount);
        sp = getSharedPreferences("MyData",0);

        nname.setText(sp.getString("SharedName", "0"));
        nnumber.setText(sp.getString("SharedNumber", "0"));
        aadd.setText(sp.getString("SharedAddress", "0"));
        eemail.setText(sp.getString("SharedEmail", "0"));
        aamount.setText(sp.getString("SharedAmount", "0"));
        ttime.setText(sp.getString("SharedTime", "0"));
        ttm.setText(sp.getString("SharedTotal", "0"));

        paynow = (Button)findViewById(R.id.pay);
        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Payment Gateway under Construction", Toast.LENGTH_LONG).show();
            }
        });
    }

}
