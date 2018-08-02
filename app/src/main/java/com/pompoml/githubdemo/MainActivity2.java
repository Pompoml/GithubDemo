package com.pompoml.githubdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Field;

public class MainActivity2 extends AppCompatActivity {

    public static final String TAG = "MainActivity2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 26; i++) {
            char tmp = (char) ('a' + i);
            try {
                Field field = h.class.getField(String.valueOf(tmp));
                System.out.println(field.toString());
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        String a = h.a;
    }

}
