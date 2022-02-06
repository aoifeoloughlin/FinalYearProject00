package com.example.finalyearproject00;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class PositivePageActivity extends AppCompatActivity {
    int addClick = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.positive_experiences);
        Button addPTextBox = findViewById(R.id.addPositiveExp);
        Button removePTextBox = findViewById(R.id.removePositiveExp);
        LinearLayout layoutP = (LinearLayout) findViewById(R.id.ll);
        EditText textBoxP = (EditText) findViewById(R.id.positiveExp01);
        addClick = 0;
        addPTextBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(addClick <= 3) {
                    addClick++;
                    layoutP.addView(createNewPosExp(textBoxP.getText().toString()));
                }
            }
        });

        removePTextBox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(addClick <= 4 && addClick >=1){
                    addClick--;
                    layoutP.removeViewAt(layoutP.getChildCount()-1);
                }
            }
        });
    }

    public void moveToHomePage(View v){
        Intent intent = new Intent(PositivePageActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private EditText createNewPosExp (String text){
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText textView = new EditText(this);
        textView.setLayoutParams(lparams);
        textView.setText("Positive Experience Input");
        return textView;
    }
}
