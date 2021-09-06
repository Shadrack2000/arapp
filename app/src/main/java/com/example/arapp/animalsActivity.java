package com.example.arapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class animalsActivity extends AppCompatActivity {
ImageView goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);

        goback=findViewById(R.id.back);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(animalsActivity.this,WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }//end of oncreate



}