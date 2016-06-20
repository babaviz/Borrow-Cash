package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.myfirstapp.shivamgupta.borrowcash.helper.SessionManager;

/**
 * Created by the master mind Mr.Shivam Gupta on 1/30/2016.
 */
public class MyAccount extends Activity implements View.OnClickListener{

    Button log_out,details;
    TextView name,caption;
    SharedPreferences sp;
    private SessionManager session;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount_layout);
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        name = (TextView)findViewById(R.id.nnname);
        caption = (TextView)findViewById(R.id.caption);
        sp = getSharedPreferences("MyData",0);

        name.setText(sp.getString("SharedName","0"));
        if(sp.getString("SharedBorrow","0").equals("0"))
        {
            caption.setText("You have registered as a Lender");
        }
        else if(sp.getString("SharedBorrow","0").equals("1"))
        {
            caption.setText("You have registered as a Borrower");
        }
        log_out =(Button)findViewById(R.id.btnLogout);
        log_out.setOnClickListener(this);
        details =(Button)findViewById(R.id.btnDetails);
        details.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnLogout:
                logoutUser();
                break;

            case R.id.btnDetails:
                Intent i = new Intent("com.myfirstapp.shivamgupta.borrowcash.DetailsActivity");
                startActivity(i);
                break;
        }
    }
    private void logoutUser() {
        session.setLogin(false);

        //db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MyAccount.this, SecondActivity.class);
        startActivity(intent);
        finish();
    }
}
