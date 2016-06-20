package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.myfirstapp.shivamgupta.borrowcash.R;
import com.myfirstapp.shivamgupta.borrowcash.app.AppConfig;
import com.myfirstapp.shivamgupta.borrowcash.app.AppController;
import com.myfirstapp.shivamgupta.borrowcash.helper.SQLiteHandler;
import com.myfirstapp.shivamgupta.borrowcash.helper.SessionManager;

/**
 * Created by the master mind Mr.Shivam Gupta on 1/16/2016.
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    Button newregistration,login;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    SharedPreferences sp;
    //SharedPrefrences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        sp = getSharedPreferences("MyData",0);
        newregistration =(Button)findViewById(R.id.btnLinkToRegisterScreen);
        newregistration.setOnClickListener(this);


        login =(Button)findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());
        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            AppController.getInstance().cancelPendingRequests("req_login");
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MyAccount.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLinkToRegisterScreen:
                Intent J = new Intent("com.myfirstapp.shivamgupta.borrowcash.RegisterActivity");
                startActivity(J);
                break;
            case R.id.btnLogin:

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                // Check for empty data in the form
                if (email.trim().length() > 0 && password.trim().length() > 0) {
                    // login user
                    //checkLogin(email, password);
                    login_user(email,password);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    private void login_user(String email, String password) {
        if(email.equals(sp.getString("SharedEmail", "0")) && password.equals(sp.getString("SharedPassword","0")))
        {
            session.setLogin(true);
            //Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this,
                    MyAccount.class);
            startActivity(intent);
        }
    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String borrower = user.getString("borrower");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String phone = user.getString("phone");
                        String prin_amount = user.getString("prin_amount");
                        String interest = user.getString("interest");
                        String total = user.getString("total");
                        String time = user.getString("time");
                        String address = user.getString("address");
                        String created_at = user.getString("created_at");

                        if(borrower.equals("1"))
                        {
                            String mort = user.getString("mortgage");
                            db.addUser(uid,"1",name, email,phone,prin_amount,
                                    interest,total,time,
                                    address,"0",created_at,mort);
                        }
                        else if(borrower.equals("0"))
                        {
                            String charity = user.getString("charity");
                            db.addUser(uid,"0",name, email,phone,prin_amount,
                                    interest,total,time,
                                    address,charity,created_at,"0");
                        }

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                MyAccount.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

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
}
