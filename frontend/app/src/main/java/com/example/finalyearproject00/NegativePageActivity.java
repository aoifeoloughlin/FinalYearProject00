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

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class NegativePageActivity extends AppCompatActivity {

    int addClick = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                connectToDB();
            }
        });
    }

    public void moveToPosPage(View vN) {
        Intent intent = new Intent(NegativePageActivity.this, PositivePageActivity.class);
        startActivity(intent);
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

    public void connectToDB() {


            /*MongoClientURI client = new MongoClientURI("mongodb+srv://oloughlinaoife1:gardenPicnic123!@finalyear.aeozg.mongodb.net");
            MongoClient mongoClient = MongoClients.create(uri)
            MongoDatabase mongoDatabase = client.getDatabase("gratitudeApp");
            MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("User");
            Document newCustomer = new Document("firstName", "Emer")
                    .append("lastName", "Mullen")
                    .append("email", "emer@simplesolution.dev")
                    .append("phone", "1234567891");
            mongoCollection.insertOne(newCustomer);*/

    }

}
