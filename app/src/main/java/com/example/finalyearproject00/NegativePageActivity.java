package com.example.finalyearproject00;

import com.example.finalyearproject00.databinding.ActivityMainBinding;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class NegativePageActivity extends AppCompatActivity {

    int addClick = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.negative_experiences);
        final Button addTextBox = findViewById(R.id.addNegativeExp);
        LinearLayout mLayout = (LinearLayout) findViewById(R.id.ll);
        EditText mEditText = (EditText) findViewById(R.id.NegativeExp01);
        TextView textView = new TextView(this);
        mEditText.setText("addClick");
        addClick = 0;
        addTextBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addClick++;
                if(addClick <= 4) {
                    mLayout.addView(createNewTextView(mEditText.getText().toString()));
                }else{
                    System.out.println("too many clicks");
                }
            }
        });
    }
        public void moveToPosPage (View vN){
            Intent intent = new Intent(NegativePageActivity.this, PositivePageActivity.class);
            startActivity(intent);
        }
        public void moveToHomePage (View v){
            Intent intent = new Intent(NegativePageActivity.this, MainActivity.class);
            startActivity(intent);
        }
        private EditText createNewTextView (String text){
            final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final EditText textView = new EditText(this);
            final TextView numberN = new TextView(this);
            textView.setLayoutParams(lparams);
            textView.setText("New text: " + text);
            return textView;
        }

    }