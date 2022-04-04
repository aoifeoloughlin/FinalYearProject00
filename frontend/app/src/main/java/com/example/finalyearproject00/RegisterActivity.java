package com.example.finalyearproject00;

import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth userAuth;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
        userAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.register_user);
        Button registerUserButton = findViewById(R.id.submitRegister);
        EditText signUpEmail = (EditText) findViewById(R.id.registerEmail);
        EditText signUpPassword = (EditText) findViewById(R.id.registerPassword);
        Button returnToLogin = findViewById(R.id.returnToLogin);
        returnToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registerUserButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                System.out.println("i have been clicked");
                String email = signUpEmail.getText().toString();
                String password = signUpPassword.getText().toString();
                System.out.println(email);
                userAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task){
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = userAuth.getCurrentUser();
                                    registerNewUser(email, password, user);
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
        });
    }
    public void registerNewUser(String email, String password, FirebaseUser user){
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://10.0.2.2:3001/newUser");
        UrlEncodedFormEntity form;
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("userId",user.getUid()));
            nameValuePairs.add(new BasicNameValuePair("userName",email));
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
}
