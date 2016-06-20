package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by the master mind Mr.Shivam Gupta on 1/16/2016.
 */
public class RegisterActivity extends Activity implements View.OnClickListener {

    Button lender,borrower;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        lender =(Button)findViewById(R.id.btnLender);
        lender.setOnClickListener(this);

        borrower =(Button)findViewById(R.id.btnBorrower);
        borrower.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLender:
                Intent J = new Intent("com.myfirstapp.shivamgupta.borrowcash.LenderActivity");
                startActivity(J);
                break;
            case R.id.btnBorrower:
                Intent i = new Intent("com.myfirstapp.shivamgupta.borrowcash.BorrowerActivity");
                startActivity(i);
                break;
        }
    }
}
