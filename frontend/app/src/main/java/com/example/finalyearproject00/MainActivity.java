package com.example.finalyearproject00;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.finalyearproject00.databinding.ActivityMainBinding;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

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
       // WebSettings negativeExpSettings = negExp.getSettings();
        //negativeExpSettings.setJavaScriptEnabled(true);

        Button posExp = findViewById(R.id.posExpView);
        //posExp.setOnClickListener(this::displayText);
       // posExp.getSettings().setJavaScriptEnabled(true);
        GraphView graphView = (GraphView) findViewById(R.id.graphOfExp);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graphView.setTitle("My Graph View");
        graphView.setTitleColor(R.color.purple_200);
        graphView.setTitleTextSize(18);
        graphView.addSeries(series);

        //WebView view = findViewById(R.id.webView);
        //view.getSettings().setJavaScriptEnabled(true);
        //view.loadUrl("file:///android_asset/" + fileName);

        getUserData();

        BottomNavigationView navView = findViewById(R.id.nav_view);
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

    public void getUserData() {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet("http://10.0.2.2:3001/getUsersData");
        UrlEncodedFormEntity form;
        BufferedReader in = null;
        try {
            HttpResponse response = httpclient.execute(httpget);
            String JSONString = EntityUtils.toString(response.getEntity(),
                        "UTF-8");
            JSONObject jsonObject = new JSONObject(JSONString); //Assuming it's a JSON Object


        } catch (ClientProtocolException e) {
            Log.e("HTTP Get", "Protocol error = " + e.toString());
        } catch (IOException e) {
            Log.e("HTTP Get", "IO error = " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            }

    }


}
