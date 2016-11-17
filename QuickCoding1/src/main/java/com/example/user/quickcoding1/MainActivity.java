package com.example.user.quickcoding1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button =(Button)findViewById(R.id.button);
        Button button2 =(Button)findViewById(R.id.button2);
        Button button3 =(Button)findViewById(R.id.button3);
        final TextView text = (TextView)findViewById(R.id.textView3);

        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                MyMinimum my1 = new MyMinimum();
                int m = my1.getResult();
                text.setText(String.valueOf(m));

            }
        });

        button2.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                MyAverage my2 = new MyAverage();
                int a = my2.getResult();
                text.setText(String.valueOf(a));

            }
        });

        button3.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "numbers = [3,1,5,9,7]", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

