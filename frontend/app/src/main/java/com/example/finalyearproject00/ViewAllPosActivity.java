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

public class ViewAllPosActivity extends AppCompatActivity{
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            setContentView(R.layout.view_all_pos);
            try {
                getAllPosExp();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Button returnToMain = findViewById(R.id.leaveResourcePage);
            returnToMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ViewAllPosActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }

        public void getAllPosExp() throws JSONException, IOException {
            String fireId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getAllUserPosExpInfo/"+fireId);
            HttpResponse responseGet = httpclient.execute(httpget);
            String responseBody = EntityUtils.toString(responseGet.getEntity());
            JSONArray posExpFromDB = new JSONArray(responseBody);

            ArrayList<String> positiveExperienceInfo = new ArrayList<String>();
            for(int i = 0; i<posExpFromDB.length(); i++){

                JSONObject experience = (JSONObject) posExpFromDB.get(i);
                String description = (String) experience.get("posExperience");

                String datePost = (String) experience.get("datePosted");
                datePost = datePost.substring(0, 10);
                String expWeight = (String) experience.get("weight").toString();
                positiveExperienceInfo.add("Positive Experience:\n Description: "+description+"\n Posted: "+datePost
                +"\n Weight: "+expWeight+"\n");
            }
            RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rlPos);

            TextView posBox = new TextView(ViewAllPosActivity.this);

            for(int j = 0; j<positiveExperienceInfo.size(); j++){
                posBox.append("\n "+ (j+1) +"\n");
                System.out.println(positiveExperienceInfo.get(j));
                posBox.append(positiveExperienceInfo.get(j));
            }

            posBox.setVerticalScrollBarEnabled(true);
            posBox.setMovementMethod(new ScrollingMovementMethod());
            posBox.setMaxLines(35);
            relativeLayout.addView(posBox);

        }

}
