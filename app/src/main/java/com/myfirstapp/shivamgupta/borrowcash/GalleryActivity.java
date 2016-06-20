package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class GalleryActivity extends ActionBarActivity {

    private EditText amount;
    private EditText time;
    private CheckBox cb,cl;
    private Button calc;
    TextView t;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Interest Calculator");

        amount = (EditText) findViewById(R.id.amt);
        time = (EditText) findViewById(R.id.time);
        cb = (CheckBox) findViewById(R.id.borrow);
        cl = (CheckBox) findViewById(R.id.lender);
        t = (TextView)findViewById(R.id.textViewcalc);
        calc = (Button)findViewById(R.id.cal);
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int amt = Integer.parseInt(amount.getText().toString());
                int ttime = Integer.parseInt(time.getText().toString());
                double t_amt;
                if(cb.isChecked())
                {
                    if(amt>=1 && amt<20000)
                    {
                        double temp = Math.pow(1.03,ttime/3);
                        t_amt = amt*temp;
                        t.setText("Total amount is Rs. "+t_amt);
                    }
                    else if(amt>=20000 && amt<100000)
                    {
                        double temp = Math.pow(41/40,ttime/3);
                        t_amt = amt*temp;
                        t.setText("Total amount is Rs. "+t_amt);
                    }
                    else if(amt>=100000 && amt<1000000)
                    {
                        double temp = Math.pow(1.02375,ttime/3);
                        t_amt = amt*temp;
                        t.setText("Total amount is Rs. "+t_amt);
                    }
                    else if(amt>=1000000)
                    {
                        double temp = Math.pow(1.023125,ttime/3);
                        t_amt = amt*temp;
                        t.setText("Total amount is Rs. "+t_amt);
                    }
                }
                else if(cl.isChecked())
                {
                    double temp = Math.pow(1.02,ttime/3);
                    t_amt = amt*temp;
                    t.setText("Total amount is Rs. "+t_amt);
                }
            }
        });
    }
}