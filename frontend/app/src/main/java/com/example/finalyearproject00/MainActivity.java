package com.example.finalyearproject00;
import static com.firebase.ui.auth.AuthUI.getInstance;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.finalyearproject00.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseUser;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
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
        getUserData();
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
        /**
         * NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
         *
         *         AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
         *                 R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
         *                 .build();
         *         NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
         *         NavigationUI.setupWithNavController(binding.navView, navController);
         */
    }
    public void moveToPosPage(View v){
        Intent intent = new Intent(MainActivity.this, PositivePageActivity.class);
        startActivity(intent);
    }
    public void moveToNegPage(View vN){
        Intent intentN = new Intent(MainActivity.this, NegativePageActivity.class);
        startActivity(intentN);
    }

    public JSONArray getUserData() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getUsersData");
        UrlEncodedFormEntity form;
        BufferedReader in = null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            String JSONString = EntityUtils.toString(response.getEntity(),
                        "UTF-8");
           JSONObject jsonObject = new JSONObject(JSONString); //Assuming it's a JSON Object

            return jsonObject.getJSONArray("positiveExpSet");
        } catch (ClientProtocolException e) {
            Log.e("HTTP Get", "Protocol error = " + e.toString());
        } catch (IOException e) {
            Log.e("HTTP Get", "IO error = " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            }

        return null;
    }

    public JSONArray getPositiveExperienceData() {
        //getting info from posexp collection
        //use the url to get the specific document
        //use the general index function from back to get all
        //using this method to get the info from the db if the user's id is correct
        // add user id to the pos exp
        //for i in range pe.array.length
        // datapoint = (i, weight)
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getPositiveExperienceData");
        UrlEncodedFormEntity form;
        BufferedReader in = null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            String JSONString = EntityUtils.toString(response.getEntity(),
                    "UTF-8");
            JSONObject jsonObject = new JSONObject(JSONString); //Assuming it's a JSON Object

            return jsonObject.getJSONArray("positiveExpSet");
        } catch (ClientProtocolException e) {
            Log.e("HTTP Get", "Protocol error = " + e.toString());
        } catch (IOException e) {
            Log.e("HTTP Get", "IO error = " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getGraphData() throws JSONException {
        JSONArray userPosExp = getUserData();
        System.out.println(userPosExp);
        String id1 = (String) userPosExp.get(0);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1)
        });
        graphView.setTitle("Positive and Negative Experiences"+ getCurrentTime().toString());
        graphView.setTitleColor(R.color.purple_200);
        graphView.setTitleTextSize(18);
        graphView.addSeries(series);
    }
    public Date getCurrentTime() {
        Date timeStamp = Calendar.getInstance().getTime();
        return timeStamp;
    }

}
