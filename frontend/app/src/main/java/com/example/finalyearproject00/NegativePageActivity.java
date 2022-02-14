package com.example.finalyearproject00;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mongodb.MongoClientURI;
import com.mongodb.ConnectionString;

import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.bson.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NegativePageActivity extends AppCompatActivity {

    int addClick = 0;

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
        addTextBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (addClick <= 3) {
                    addClick++;
                    layout.addView(createNewNegExp(textBox.getText().toString()));
                }
            }
        });

        removeTextBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (addClick <= 4 && addClick >= 1) {
                    addClick--;
                    layout.removeViewAt(layout.getChildCount() - 1);
                }
            }
        });

        submitDB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                connectToDB(textBox.getText().toString());
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
        textView.setText("Negative Experience Input");
        return textView;
    }

    public void connectToDB(String textBox) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://10.0.2.2:3001/newNegExp");
            UrlEncodedFormEntity form;
            try {
                Date now = getCurrentTime();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("negExperience",textBox));
                nameValuePairs.add(new BasicNameValuePair("datePosted",now.toString()));

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

}
