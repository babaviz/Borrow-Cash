package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by the master mind Mr.Shivam Gupta on 2/3/2016.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myfirstapp.shivamgupta.borrowcash.R;


/**
 * Created by the master mind Mr.Shivam Gupta on 16/09/2015.
 */
public class ContactUs extends ActionBarActivity {

    private EditText toEmail = null;
    private EditText emailSubject = null;
    private EditText emailBody = null;
    Button send;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Us");

        toEmail = (EditText) findViewById(R.id.toEmail);
        emailSubject = (EditText) findViewById(R.id.subject);
        emailBody = (EditText) findViewById(R.id.emailBody);
        send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = toEmail.getText().toString();
                String subject = emailSubject.getText().toString();
                String message = emailBody.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[] {"f2014154@pilani.bits-pilani.ac.in"});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT,from+"\n"+message);

                // need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Choose an Email client"));
                Toast.makeText(ContactUs.this,"Thanks for contacting Us...We will get back to you soon",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
