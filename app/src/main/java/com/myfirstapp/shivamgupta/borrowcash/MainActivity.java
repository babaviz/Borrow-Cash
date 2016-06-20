package com.myfirstapp.shivamgupta.borrowcash;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    Thread timer = new Thread()
    {
        public void run()
        {
            try
            {
                sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            finally
            {
                Intent i = new Intent("com.myfirstapp.shivamgupta.borrowcash.SecondActivity");
                startActivity(i);
            }
        }
    };

    timer.start();
}

}
