package com.example.arapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.button2);
        button.setOnClickListener(view -> {
                next();
        });

    }//end of oncreate

    int counter =0;
    @Override
    public void onBackPressed() {
        counter++;
        if(counter==1){
            Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show();
        }
        else{
            super.onBackPressed();
        }

    }

    public void next() {
        Intent intent=new Intent(MainActivity.this,Register.class);
        startActivity(intent);
        finish();
    }

}