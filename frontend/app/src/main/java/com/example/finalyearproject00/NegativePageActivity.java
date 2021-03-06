package com.example.finalyearproject00;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NegativePageActivity extends AppCompatActivity {

    int addClick = 0;
    String weightValue;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        setContentView(R.layout.negative_experiences);
        Button addTextBox = findViewById(R.id.addNegativeExp);
        Button removeTextBox = findViewById(R.id.removeNegativeExp);
        Button submitDB = findViewById(R.id.submitNegativeExp);
        LinearLayout layout = (LinearLayout) findViewById(R.id.ll);
        EditText textBox = (EditText) findViewById(R.id.NegativeExp01);
        addClick = 0;
        Spinner dropdown = findViewById(R.id.spinner);
        String[] items = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        LinearLayout layoutDropdown = (LinearLayout) findViewById(R.id.dropdownLayout);
        addTextBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (addClick <= 3) {
                    addClick++;
                    layout.addView(createNewNegExp(textBox.getText().toString()));
                    layoutDropdown.addView(createDropdown());
                }
            }
        });

        removeTextBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (addClick <= 4 && addClick >= 1) {
                    addClick--;
                    layout.removeViewAt(layout.getChildCount() - 1);
                    layoutDropdown.removeViewAt(layoutDropdown.getChildCount()-1);
                }
            }
        });

        submitDB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(addClick == 0){
                    weightValue = dropdown.getSelectedItem().toString();
                    if(weightValue == null ){
                        weightValue = String .valueOf(1);
                    }
                    System.out.println(textBox.getText().toString());
                    connectToDB(textBox.getText().toString(),weightValue);
                }

                for(int i = 0; i<layout.getChildCount(); i++){
                    weightValue = dropdown.getSelectedItem().toString();
                    if(weightValue == null ){
                        weightValue = String.valueOf(1);
                    }

                    connectToDB(textBox.getText().toString(),weightValue);
                    EditText childExp = (EditText) layout.getChildAt(i);
                    Spinner childWeight = (Spinner) layoutDropdown.getChildAt(i);
                    String cWeight = childWeight.getSelectedItem().toString();
                    if(cWeight == null ){
                        cWeight = String.valueOf(1);
                    }
                    connectToDB(childExp.getText().toString(), cWeight);
                }
                Intent intent = new Intent(NegativePageActivity.this, PositivePageActivity.class);
                startActivity(intent);
            }
        });
    }

    public void moveToHomePage(View v) {
        Intent intent = new Intent(NegativePageActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private EditText createNewNegExp(String text) {
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText textView = new EditText(this);
        textView.setLayoutParams(lparams);
        textView.setHint("Negative Experience Input");
        return textView;
    }

    public void connectToDB(String textBox, String weight) {
            HttpClient httpclient = new DefaultHttpClient();
            String userFireId = user.getUid();
            HttpPost httppost = new HttpPost("http://10.0.2.2:3001/newNegExp");
            UrlEncodedFormEntity form;
            try {
                Date now = getCurrentTime();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("negExperience",textBox));
                nameValuePairs.add(new BasicNameValuePair("datePosted",now.toString()));
                nameValuePairs.add(new BasicNameValuePair("weight", weight));
                nameValuePairs.add(new BasicNameValuePair("userId", userFireId));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);

                Log.i("HTTP Post", "Response from server node = " + response.getStatusLine().getReasonPhrase() + "  Code = " + response.getStatusLine().getStatusCode());
            } catch (ClientProtocolException e) {
                Log.e("HTTP Post", "Protocol error = " + e.toString());
            } catch (IOException e) {
                Log.e("HTTP Post", "IO error = " + e.toString());
            }
    }

    public Date getCurrentTime() {
        Date timeStamp = Calendar.getInstance().getTime();
       return timeStamp;
    }

    private Spinner createDropdown(){
        Spinner dropdown = new Spinner(this);
        String[] items = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> spin = new ArrayAdapter<>(NegativePageActivity.this, android.R.layout.simple_spinner_dropdown_item,
                items);
        dropdown.setAdapter(spin);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dropdown.setLayoutParams(lparams);
        return dropdown;
    }
}
