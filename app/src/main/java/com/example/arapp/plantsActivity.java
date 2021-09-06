package com.example.arapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class plantsActivity extends AppCompatActivity {
ImageView goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);


        goback=findViewById(R.id.back);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(plantsActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }//end of oncreate

    int counter =0;
    @Override
    public void onBackPressed() {
        counter++;
        if (counter == 1) {
            Toast.makeText(this, "Press again to go back", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent=new Intent(plantsActivity.this,WelcomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}