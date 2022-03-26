package com.example.finalyearproject00;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class ViewAllNegActivity extends AppCompatActivity{
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.view_all_neg);
        try {
            getAllNegExp();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Button returnToMain = findViewById(R.id.leaveResourcePageNeg);
        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllNegActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getAllNegExp() throws JSONException, IOException {
        String fireId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getAllUserNegExpInfo/"+fireId);
        HttpResponse responseGet = httpclient.execute(httpget);
        String responseBody = EntityUtils.toString(responseGet.getEntity());
        JSONArray negExpFromDB = new JSONArray(responseBody);

        ArrayList<String> negativeExperienceInfo = new ArrayList<String>();
        for(int i = 0; i<negExpFromDB.length(); i++){

            JSONObject experience = (JSONObject) negExpFromDB.get(i);
            String description = (String) experience.get("negExperience");

            String datePost = (String) experience.get("datePosted");
            datePost = datePost.substring(0, 10);
            String expWeight = (String) experience.get("weight").toString();
            negativeExperienceInfo.add("Positive Experience:\n Description: "+description+"\n Posted: "+datePost
                    +"\n Weight: "+expWeight+"\n");
        }
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rlNeg);

        TextView negBox = new TextView(ViewAllNegActivity.this);

        for(int j = 0; j<negativeExperienceInfo.size(); j++){
            negBox.append("\n "+ (j+1) +"\n");
            negBox.append(negativeExperienceInfo.get(j));
        }

        negBox.setVerticalScrollBarEnabled(true);
        negBox.setMovementMethod(new ScrollingMovementMethod());
        negBox.setMaxLines(33);
        relativeLayout.addView(negBox);

    }

}