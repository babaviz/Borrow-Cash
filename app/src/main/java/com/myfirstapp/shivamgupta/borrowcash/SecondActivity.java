package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Gallery;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by the master mind Mr.Shivam Gupta on 1/16/2016.
 */
public class SecondActivity extends ActionBarActivity implements View.OnClickListener{

    Button register,sucess,contact;
    Bitmap bitmap1,circularBitmap1,bitmap2,circularBitmap2,bitmap3,circularBitmap3,bitmap4,circularBitmap4;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.second_layout);

        register =(Button)findViewById(R.id.register);
        register.setOnClickListener(this);

        sucess =(Button)findViewById(R.id.success);
        sucess.setOnClickListener(this);

        contact =(Button)findViewById(R.id.contact);
        contact.setOnClickListener(this);

        ImageView one = (ImageView)findViewById(R.id.one);
        ImageView two = (ImageView)findViewById(R.id.two);
        ImageView three = (ImageView)findViewById(R.id.three);
        ImageView four = (ImageView)findViewById(R.id.four);

        bitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.bor1);
        circularBitmap1 = ImageConverter.getRoundedCornerBitmap(bitmap1, 50);

        bitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.b22);
        circularBitmap2 = ImageConverter.getRoundedCornerBitmap(bitmap2, 50);

        bitmap3 = BitmapFactory.decodeResource(this.getResources(), R.drawable.b33);
        circularBitmap3 = ImageConverter.getRoundedCornerBitmap(bitmap3, 50);

        bitmap4 = BitmapFactory.decodeResource(this.getResources(), R.drawable.b44);
        circularBitmap4 = ImageConverter.getRoundedCornerBitmap(bitmap4, 50);

        one.setImageBitmap(circularBitmap1);
        two.setImageBitmap(circularBitmap2);
        three.setImageBitmap(circularBitmap3);
        four.setImageBitmap(circularBitmap4);
    }

    @Override
    public void onClick(View v) {
       switch (v.getId())
        {
            case R.id.register:
                Intent J = new Intent("com.myfirstapp.shivamgupta.borrowcash.LoginActivity");
                startActivity(J);
                break;
            case R.id.success:
                Intent Ii = new Intent("com.myfirstapp.shivamgupta.borrowcash.GalleryActivity");
                startActivity(Ii);
                break;
            case R.id.contact:
                Intent I = new Intent("com.myfirstapp.shivamgupta.borrowcash.ContactUs");
                startActivity(I);
                break;
        }
    }
}