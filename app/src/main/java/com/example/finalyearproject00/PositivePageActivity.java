package com.example.finalyearproject00;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class PositivePageActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.positive_experiences);
    }

    public void moveToHomePage(View v){
        Intent intent = new Intent(PositivePageActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
