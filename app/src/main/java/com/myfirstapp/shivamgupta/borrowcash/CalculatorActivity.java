package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by the master mind Mr.Shivam Gupta on 1/16/2016.
 */
public class CalculatorActivity extends Activity implements View.OnClickListener {

    Button calculaotr;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.calculate_layout);

        calculaotr =(Button)findViewById(R.id.btcalculate);
        calculaotr.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btcalculate:

                Intent J = new Intent("com.myfirstapp.shivamgupta.borrowcash.SecondActivity");
                startActivity(J);
                break;

        }
    }
}