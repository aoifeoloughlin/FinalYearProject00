package com.example.finalyearproject00;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PopupActivity extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        setContentView(R.layout.popup);
        Button returnToMain = findViewById(R.id.leaveResourcePage);
        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupActivity.this, MainActivity.class);
                 startActivity(intent);
            }
        });

        TextView headspaceLink = (TextView) findViewById(R.id.headspace);
        headspaceLink.setMovementMethod(LinkMovementMethod.getInstance());

        TextView yogaLink = (TextView) findViewById(R.id.yoga);
        yogaLink.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
