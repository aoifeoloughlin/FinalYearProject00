package com.example.finalyearproject00;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject00.databinding.ActivityMainBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    GraphView graphView;
    private Button posExp, negExp;
    private FirebaseAuth userAuth;

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
        setContentView(R.layout.activity_main);
        Button negExp = findViewById(R.id.negExpView);
        Button posExp = findViewById(R.id.posExpView);
        Button viewAllPos = findViewById(R.id.viewAllPos);
        viewAllPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAllPosActivity.class);
                startActivity(intent);
            }
        });

        Button viewAllNeg = findViewById(R.id.viewAllNeg);
        viewAllNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAllNegActivity.class);
                startActivity(intent);
            }
        });
        Button resources = findViewById(R.id.helpfulResources);
        graphView = (GraphView) findViewById(R.id.graphOfExp);
        graphView.setHorizontalScrollBarEnabled(true);
        graphView.getViewport().setScrollable(true);
        graphView.getViewport().isScrollable();
        try {
            updatePositiveUserInfo();
            updateNegativeUserInfo();
            getGraphData(graphView);
            String ratioAnnounce = "User's Ratio is: "+calculateRatio();
            Toast toast=Toast.makeText(getApplicationContext(),ratioAnnounce, Toast. LENGTH_SHORT);
            toast.show();
            toast.setDuration(Toast.LENGTH_LONG);
            if(calculateRatio() < 3){
                resources.setVisibility(View.VISIBLE);
                resources.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, PopupActivity.class);
                        startActivity(intent);
                    }
                });
            }else{
                resources.setVisibility(View.INVISIBLE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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

    public void updatePositiveUserInfo() throws IOException, JSONException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser userF = auth.getCurrentUser();
        String fireId = userF.getUid();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getAllUserPosExp/"+fireId);

        HttpResponse responseGet = httpclient.execute(httpget);
        String responseBody = EntityUtils.toString(responseGet.getEntity());

        JSONArray jA = new JSONArray(responseBody);
        String resBodyPos = "";
        for (int i = 0; i < jA.length(); i++) {
            resBodyPos += jA.get(i).toString()+"&";
        }
        HttpPut httpput = new HttpPut("http://10.0.2.2:3001/updateUserPosExp/"+fireId+"/"+resBodyPos);
        HttpResponse responsePut = httpclient.execute(httpput);
        UrlEncodedFormEntity form;
        BufferedReader in = null;
    }

    public void updateNegativeUserInfo() throws IOException, JSONException {
        String fireId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getAllUserNegExp/"+fireId);
        HttpResponse responseGet = httpclient.execute(httpget);
        String responseBody = EntityUtils.toString(responseGet.getEntity());

        JSONArray jAN = new JSONArray(responseBody);
        String resBodyNeg = "";
        for (int i = 0; i < jAN.length(); i++) {
            resBodyNeg += jAN.get(i).toString()+"&";
        }
        HttpPut httpput = new HttpPut("http://10.0.2.2:3001/updateUserNegExp/"+fireId+"/"+resBodyNeg);
        HttpResponse responsePut = httpclient.execute(httpput);
        UrlEncodedFormEntity form;
        BufferedReader in = null;
    }

    public void getGraphData(GraphView graphView) throws JSONException, IOException {
        String fireId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getAllUserPosExpWeights/"+fireId);
        HttpResponse responseGet = httpclient.execute(httpget);
        String responseBody = EntityUtils.toString(responseGet.getEntity());
        JSONArray posExpGraph = new JSONArray(responseBody);
        PointsGraphSeries<DataPoint> seriesPos = new PointsGraphSeries<>(getPosPoints(posExpGraph));
        HttpGet httpgetNEG = new HttpGet("http://10.0.2.2:3001/getAllUserNegExpWeights/"+fireId);
        HttpResponse responseGetNEG = httpclient.execute(httpgetNEG);
        String responseBodyNEG = EntityUtils.toString(responseGetNEG.getEntity());
        JSONArray negExpGraph = new JSONArray(responseBodyNEG);
        PointsGraphSeries<DataPoint> seriesNeg = new PointsGraphSeries<>(getNegPoints(negExpGraph));
        graphView.setTitle("Positive and Negative Experiences");
        graphView.setTitleColor(R.color.black);
        graphView.setTitleTextSize(40);
        graphView.addSeries(seriesPos);
        graphView.setHorizontalScrollBarEnabled(true);
        seriesPos.setSize(20);
        seriesPos.setShape(PointsGraphSeries.Shape.POINT);
        graphView.addSeries(seriesNeg);
        seriesNeg.setShape(PointsGraphSeries.Shape.POINT);
        seriesNeg.setSize(20);
        seriesPos.setColor(Color.GREEN);
        seriesNeg.setColor(Color.RED);
    }
    public Date getCurrentTime() {
        Date timeStamp = Calendar.getInstance().getTime();
        return timeStamp;
    }

    public DataPoint[] getPosPoints(JSONArray posExpGraph) throws JSONException {
        DataPoint[] values = new DataPoint[posExpGraph.length()];
        if((posExpGraph.length())!=0){
            for(int i = 0; i<posExpGraph.length(); i++) {
                int weightPos = (int) posExpGraph.get(i);
                values[i] = new DataPoint((i+1), weightPos);
            }}
        return values;
    }

    public DataPoint[] getNegPoints(JSONArray negExpGraph) throws JSONException {
        DataPoint[] values = new DataPoint[negExpGraph.length()];
        if((negExpGraph.length())!=0){
            for(int i = 0; i<negExpGraph.length(); i++) {
                int weightNeg = (int) negExpGraph.get(i);
                values[i] = new DataPoint((i+1), weightNeg);
            }}
        return values;
    }

    public int calculateRatio() throws JSONException, IOException {
        String fireId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getAllUserPosExpWeights/"+fireId);
        HttpResponse responseGet = httpclient.execute(httpget);
        String responseBody = EntityUtils.toString(responseGet.getEntity());
        JSONArray posWeight = new JSONArray(responseBody);
        HttpGet httpgetNEG = new HttpGet("http://10.0.2.2:3001/getAllUserNegExpWeights/"+fireId);
        HttpResponse responseGetNEG = httpclient.execute(httpgetNEG);
        String responseBodyNEG = EntityUtils.toString(responseGetNEG.getEntity());
        JSONArray negWeight = new JSONArray(responseBodyNEG);

        int totalWeightValuePos = 0;
        int totalWeightValueNeg = 0;

        for(int i = 0; i<posWeight.length(); i++){
            totalWeightValuePos += posWeight.getInt(i);
        }
        for(int j = 0; j<negWeight.length(); j++){
            totalWeightValueNeg += negWeight.getInt(j);
        }
        int posWeightTotalScore=0;
        int negWeightTotalScore=0;

        if(totalWeightValueNeg ==0 && totalWeightValuePos ==0){
            posWeightTotalScore=3;
            negWeightTotalScore=1;
        }
        else if(totalWeightValueNeg == 0){
            negWeightTotalScore =1;
        }else if (totalWeightValuePos == 0){
            posWeightTotalScore =1;
        }else{
            posWeightTotalScore = totalWeightValuePos * (posWeight.length());
            negWeightTotalScore = totalWeightValueNeg * (negWeight.length());
        }
        return (int) (posWeightTotalScore/negWeightTotalScore);
    }
}
