package com.example.arapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    Button logout;
    ImageButton animals, plants, humanAnatomy;
    ProgressDialog progressDialog3;
    ProgressBar progressBar;
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        logout=findViewById(R.id.btnLogout);
        animals=findViewById(R.id.imgbtnanimals);
        plants=findViewById(R.id.imgbtplants);
        humanAnatomy=findViewById(R.id.imgbthuman);


        progressDialog3=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();



        logout.setOnClickListener(view -> {
            progressDialog3.setTitle("Log out");
            progressDialog3.setMessage("Loggin out");
            progressDialog3.show();

            signout();
        });
        animals.setOnClickListener(view ->{
            openAnimals();
        });
        plants.setOnClickListener(view ->{
            openPlants();
        });
        humanAnatomy.setOnClickListener(view ->{
            openHumanAnataomy();
        });
    }//end of oncreate
//human anatomy
    private void openHumanAnataomy() {

        //initialize progress dialog
        progressDialog = new ProgressDialog(WelcomeActivity.this);
        progressDialog.show();
        //set content view
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );


        Thread timer = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2000); //delay by 2 seconds then start intent to open human anatomy activity
                    Intent intent=new Intent(WelcomeActivity.this,humanAnatomyActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                    finish();
                    super.run();
                } catch (InterruptedException e){
                    e.printStackTrace();
                } //catch errors

            }
        };
        timer.start();

    }//end of human anatomy method



    //plants
    private void openPlants() {
        //initialize progress dialog
        progressDialog = new ProgressDialog(WelcomeActivity.this);
        progressDialog.show();
        //set content view
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );


        Thread timer = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2000); //delay by 2 seconds then start intent to open plants activity
                    Intent intent=new Intent(WelcomeActivity.this,plantsActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();
                    finish();
                    super.run();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        timer.start();

    }//end of plants method



    //animals
    private void openAnimals() {
        //initialize progress dialog
        progressDialog = new ProgressDialog(WelcomeActivity.this);
        progressDialog.show();
        //set content view
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );


        Thread timer = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(2000); //delay by 2 seconds then start intent to open animals activity
                    Intent intent=new Intent(WelcomeActivity.this,animalsActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss(); //dismiss progress dialog after successful opening of animals activity
                    finish();
                    super.run();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                //we will check it later..kuna mahali nakimbia..ok?

            }
        };
        timer.start();

    }//end of animals method



    public  void  signout(){
        progressDialog3.dismiss();
        mAuth.signOut();
        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        Toast.makeText(WelcomeActivity.this,"Logged out",Toast.LENGTH_SHORT).show();
    }






}