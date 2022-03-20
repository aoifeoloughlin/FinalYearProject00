package com.example.finalyearproject00;
import static com.firebase.ui.auth.AuthUI.getInstance;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject00.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    GraphView graphView;
    private Button posExp, negExp;
    private FirebaseAuth userAuth;

    public String fileName = "homepageDesign.html";
//C:\Users\Oloug\AndroidStudioProjects\FinalYearProject00\app\src\main\assets\homepageDesign.html



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button negExp = findViewById(R.id.negExpView);
        Button posExp = findViewById(R.id.posExpView);
        GraphView graphView = (GraphView) findViewById(R.id.graphOfExp);
        try {
            updateUserInfo();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Button signOut = findViewById(R.id.signOutButton);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    AuthUI.getInstance()
                    .signOut(MainActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // user is now signed out
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
            }
        });
    }
    public void moveToPosPage(View v){
        Intent intent = new Intent(MainActivity.this, PositivePageActivity.class);
        startActivity(intent);
    }
    public void moveToNegPage(View vN){
        Intent intentN = new Intent(MainActivity.this, NegativePageActivity.class);
        startActivity(intentN);
    }

    public JSONArray getUserData() throws IOException, JSONException {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        HttpClient httpclient = new DefaultHttpClient();
        String userFireId = user.getUid();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getUsersData/:"+userFireId);
        UrlEncodedFormEntity form;
        BufferedReader in = null;
        HttpResponse response = httpclient.execute(httpget);
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONArray jsonObject = new JSONArray(responseBody);
        return jsonObject;
    }

    public JSONObject getPositiveExperienceData(String posId) throws IOException, JSONException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getPositiveExperienceData/:"+posId);
        UrlEncodedFormEntity form;
        BufferedReader in = null;
        HttpResponse response = httpclient.execute(httpget);
        String responseBody = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject;
    }

    public void updateUserInfo() throws IOException, JSONException {
        String fireId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getAllUserPosExp/"+fireId);
        HttpResponse responseGet = httpclient.execute(httpget);
        String responseBody = EntityUtils.toString(responseGet.getEntity());

        JSONArray jA = new JSONArray(responseBody);
        //fix the second part by splitting the array string with & and then in the node re split it with &
        //and save the ids to the array

        //to do: get the pos exp data with the ids in the users' thing

        // to do: on create get user ratio if it is less than 3 alert (""watch youtube"")
        //else nothing

        //to do:
        HttpPut httpput = new HttpPut("http://10.0.2.2:3001/updateUserPosExp/"+fireId+"/"+responseBody);
        HttpResponse responsePut = httpclient.execute(httpput);
        UrlEncodedFormEntity form;
        BufferedReader in = null;
    }

    public void getGraphData() throws JSONException, IOException {
        JSONArray userData = getUserData();
        System.out.println(userData);
        JSONArray posExpGraph = userData.getJSONArray(4);
        JSONArray negExpGraph = userData.getJSONArray(5);
        LineGraphSeries<DataPoint> seriesPos = new LineGraphSeries<DataPoint>();
        if((posExpGraph.length())!=0){
        for(int i = 0; i<posExpGraph.length()-1; i++) {
            JSONObject posDocument = getPositiveExperienceData((String) posExpGraph.get(i));
            int weightPos = posDocument.getInt("weight");
            seriesPos.appendData(new DataPoint(i, weightPos), true, posExpGraph.length());
        }}
        LineGraphSeries<DataPoint> seriesNeg = new LineGraphSeries<DataPoint>();
        if((negExpGraph.length())!= 0){
        for(int i = 0; i<negExpGraph.length()-1; i++) {
            JSONObject negDocument = getPositiveExperienceData((String) posExpGraph.get(i));
            int weightNeg = negDocument.getInt("weight");
            seriesNeg.appendData(new DataPoint(i, weightNeg), true, negExpGraph.length());
        }}
        graphView.setTitle("Positive and Negative Experiences"+ getCurrentTime().toString());
        graphView.setTitleColor(R.color.purple_200);
        graphView.setTitleTextSize(18);
        graphView.addSeries(seriesPos);
        graphView.addSeries(seriesNeg);
        seriesPos.setColor(5624578);
        seriesNeg.setColor(2457056);
    }
    public Date getCurrentTime() {
        Date timeStamp = Calendar.getInstance().getTime();
        return timeStamp;
    }



}
