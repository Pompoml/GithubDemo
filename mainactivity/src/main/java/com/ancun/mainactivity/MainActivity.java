package com.ancun.mainactivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ancun.mainactivity.doub.HandLineWindow;

public class MainActivity extends AppCompatActivity {

    private com.ancun.mainactivity.hand.HandLineWindow mHandLineWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {
                //permission();
                HandLineWindow.getInstance(MainActivity.this.getApplicationContext()).start();
                //mHandLineWindow = new HandLineWindow(MainActivity.this.getApplicationContext());
                //mHandLineWindow.showWindowManager();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HandLineWindow.getInstance(MainActivity.this.getApplicationContext()).end();
            }
        });

        //HandLineView handLineView = (HandLineView) findViewById(R.id.view);


    }

    public void permission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivity(intent);
            } else {
                //Android6.0以上

            }
        } else {
            //Android6.0以下，不用动态声明权限

        }
    }

}
