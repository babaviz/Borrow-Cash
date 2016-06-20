package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.myfirstapp.shivamgupta.borrowcash.app.AppConfig;
import com.myfirstapp.shivamgupta.borrowcash.app.AppController;
import com.myfirstapp.shivamgupta.borrowcash.helper.SQLiteHandler;
import com.myfirstapp.shivamgupta.borrowcash.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BorrowerActivity extends Activity implements View.OnClickListener {

    Button submit;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    EditText name,number,add,email,amount,mortage,date,pass,cpass;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    SharedPreferences sp;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.borrow_layout);

        submit =(Button)findViewById(R.id.btnRegister);
        submit.setOnClickListener(this);
        name =(EditText)findViewById(R.id.name);
        number =(EditText)findViewById(R.id.phone);
        add =(EditText)findViewById(R.id.college);
        email =(EditText)findViewById(R.id.email);
        amount =(EditText)findViewById(R.id.amount);
        mortage =(EditText)findViewById(R.id.mortage);
        date =(EditText)findViewById(R.id.date);
        pass =(EditText)findViewById(R.id.password);
        cpass =(EditText)findViewById(R.id.cpassword);
        sp = getSharedPreferences("MyData",0);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(BorrowerActivity.this,
                    MyAccount.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnRegister:
                String nname = name.getText().toString().trim();
                String nnumber = number.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String address = add.getText().toString().trim();
                String eemail = email.getText().toString().trim();
                String aamount = amount.getText().toString().trim();
                String mmortage = mortage.getText().toString().trim();
                String ddate = date.getText().toString().trim();
                String cpassword = cpass.getText().toString().trim();
                if (!nname.isEmpty() && !eemail.isEmpty() && !password.isEmpty()) {
                    if(password.equals(cpassword)) {
                        //registerUser("1", nname, eemail, nnumber, aamount, ddate, address, mmortage, password);
                        double t_amt = calculator(Integer.parseInt(ddate),Integer.parseInt(aamount));
                        storeUser("1", nname, eemail, nnumber, aamount, ddate, address, mmortage, password,""+t_amt);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                "Passwords don't match!", Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your details!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    private void registerUser(final String borrow, final String name, final String email, final String number, final String amount,
                              final String time, final String address,final String mortage, final String password) {
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    //Toast.makeText(getApplicationContext(), "Before if", Toast.LENGTH_LONG).show();
                    JSONObject jObj = new JSONObject(response);
                    //Toast.makeText(getApplicationContext(), "Before if2", Toast.LENGTH_LONG).show();
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                       // Toast.makeText(getApplicationContext(), "Adding data", Toast.LENGTH_LONG).show();
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String phone = user.getString("phone");
                        String prin_amount = user.getString("prin_amount");
                        String interest = user.getString("interest");
                        String total = user.getString("total");
                        String time = user.getString("time");
                        String address = user.getString("address");
                        String created_at = user.getString("created_at");
                        String mort = user.getString("mortgage");
                        // Inserting row in users table
                        //Toast.makeText(getApplicationContext(), "Adding data", Toast.LENGTH_LONG).show();
                        db.addUser(uid,"1",name, email,phone,prin_amount,
                                interest,total,time,
                                address,"0",created_at,mort);
                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
                        // Launch main activity
                        Intent intent = new Intent(BorrowerActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    //e.printStackTrace();
                   // Toast.makeText(getApplicationContext(),
                     //       "Hello", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();

                params.put("borrower", borrow);
                params.put("name", name);
                params.put("email", email);
                params.put("phone", number);
                params.put("prin_amount", amount);
                params.put("time", time);
                params.put("address", address);
                params.put("password", password);
                params.put("mortgage",mortage);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    private void storeUser(final String borrow, final String name, final String email, final String number, final String amount,
                              final String time, final String address,final String mortage, final String password,String t_amt)
    {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("SharedBorrow",borrow);
        editor.putString("SharedName",name);
        editor.putString("SharedEmail",email);
        editor.putString("SharedNumber",number);
        editor.putString("SharedAmount",amount);
        editor.putString("SharedTime",time);
        editor.putString("SharedAddress",address);
        editor.putString("SharedTotal",t_amt);
        editor.putString("SharedMortage",mortage);
        editor.putString("SharedPassword",password);
        editor.commit();
        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(BorrowerActivity.this,
                LoginActivity.class);
        startActivity(intent);
    }

    double calculator(int ttime,int amt)
    {
        double t_amt = 0;
        if(amt>=1 && amt<20000)
        {
            double temp = Math.pow(1.03,ttime/3);
            t_amt = amt*temp;
           // t.setText("Total amount is Rs. "+t_amt);
        }
        else if(amt>=20000 && amt<100000)
        {
            double temp = Math.pow(41/40,ttime/3);
            t_amt = amt*temp;
            //t.setText("Total amount is Rs. "+t_amt);
        }
        else if(amt>=100000 && amt<1000000)
        {
            double temp = Math.pow(1.02375,ttime/3);
            t_amt = amt*temp;
            //t.setText("Total amount is Rs. "+t_amt);
        }
        else if(amt>=1000000)
        {
            double temp = Math.pow(1.023125,ttime/3);
            t_amt = amt*temp;
            //t.setText("Total amount is Rs. "+t_amt);
        }
        return t_amt;
    }
}