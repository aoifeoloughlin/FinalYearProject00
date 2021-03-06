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

public class PositivePageActivity extends AppCompatActivity {
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
        setContentView(R.layout.positive_experiences);
        Button submitDB = findViewById(R.id.submitPositiveExp);
        EditText textBox = (EditText) findViewById(R.id.positiveExp01);
        Button addPTextBox = findViewById(R.id.addPositiveExp);
        Button removePTextBox = findViewById(R.id.removePositiveExp);
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        LinearLayout layoutP = (LinearLayout) findViewById(R.id.pll);
        LinearLayout layoutDropdown = (LinearLayout) findViewById(R.id.dropdownLayout);
        EditText textBoxP = (EditText) findViewById(R.id.positiveExp01);
        addClick = 0;
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
                for(int i = 0; i<layoutP.getChildCount(); i++){
                    if(i==0){
                        weightValue = dropdown.getSelectedItem().toString();
                        if(weightValue == null ){
                            weightValue = String .valueOf(1);
                        }
                        System.out.println(textBox.getText().toString());
                        connectToDB(textBox.getText().toString(),weightValue);
                    }

                    EditText childExp = (EditText) layoutP.getChildAt(i);
                    Spinner childWeight = (Spinner) layoutDropdown.getChildAt(i);
                    String cWeight = childWeight.getSelectedItem().toString();
                    if(cWeight == null ){
                        cWeight = String.valueOf(1);
                    }
                    connectToDB(childExp.getText().toString(), cWeight);
                }
                Intent intent = new Intent(PositivePageActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        addPTextBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(addClick <= 3) {
                    addClick++;
                    layoutP.addView(createNewPosExp(textBoxP.getText().toString()));
                    layoutDropdown.addView(createDropdown());
                }
            }
        });

        removePTextBox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(addClick <= 4 && addClick >=1){
                    addClick--;
                    layoutP.removeViewAt(layoutP.getChildCount()-1);
                    layoutDropdown.removeViewAt(layoutDropdown.getChildCount()-1);
                }
            }
        });


    }

    public void connectToDB(String textBox, String weight) {
        HttpClient httpclient = new DefaultHttpClient();
        String userFireId = user.getUid();
        HttpPost httppost = new HttpPost("http://10.0.2.2:3001/newPosExp");
        UrlEncodedFormEntity form;

        try {
            Date now = getCurrentTime();

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("posExperience",textBox));
            nameValuePairs.add(new BasicNameValuePair("datePosted",now.toString()));
            nameValuePairs.add(new BasicNameValuePair("weight", weight));
            nameValuePairs.add(new BasicNameValuePair("userId", userFireId));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);

            Log.i("HTTP Post", "Response from server node = " +
                    response.getStatusLine().getReasonPhrase() + "  Code = " +
                    response.getStatusLine().getStatusCode());
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

    public void moveToHomePage(View v) {
        Intent intent = new Intent(PositivePageActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private EditText createNewPosExp(String text){
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText textView = new EditText(this);
        textView.setLayoutParams(lparams);
        textView.setHint("Positive Experience Input");

        return textView;
    }

    private Spinner createDropdown(){
        Spinner dropdown = new Spinner(this);
        String[] items = new String[]{"1", "2", "3", "4", "5"};
        ArrayAdapter<String> spin = new ArrayAdapter<>(PositivePageActivity.this, android.R.layout.simple_spinner_dropdown_item,
                items);
        dropdown.setAdapter(spin);
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dropdown.setLayoutParams(lparams);
        return dropdown;
    }


}
