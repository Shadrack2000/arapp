package com.example.arapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextView gotoregister;
    TextView forgotpass;

    Button login;
    EditText email;
    EditText password;


    FirebaseAuth mAuth;

    //progress dialog
    ProgressDialog progressDialog;

    ProgressDialog progressDialog2;

    //For change on state (start and stop)
    FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gotoregister=findViewById(R.id.txtGotoregister);

        login=findViewById(R.id.btnLogin);
        email=findViewById(R.id.edtEmail);
        password=findViewById(R.id.edtPassword);
        forgotpass=findViewById(R.id.txtForgotpass);

        mAuth= FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this); //for login button
        progressDialog2=new ProgressDialog(this);//for reset password YES click

        //firebase auth listener
        firebaseAuthListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null)//if user is logged in
                {
                    Intent intent=new Intent(Login.this,WelcomeActivity.class);//open the Welcome activity if user was already logged in
                    startActivity(intent);
                    Toast.makeText(Login.this,"Welcome back",Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        };//end of firebase auth listener

        //don't have an account text view
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open register activity
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
                finish();
            }
        });

        //login button listener
        login.setOnClickListener(view ->{
            logUser();
        });


        //forgot password reset
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create an edit text field to let user enter their email to get reset link
                final EditText resetEmail = new EditText(v.getContext());
                final AlertDialog.Builder resetDialog = new AlertDialog.Builder(v.getContext());
                //give title and message to the alert dialog
                resetDialog.setTitle("Reset password");
                resetDialog.setMessage("Enter the email you registered with to get a reset link");
                //set the edit text to the view of the alert dialog
                resetDialog.setView(resetEmail);

                //handle the buttons on reset dialog (Yes and No)
                //Yes button
                progressDialog2.setTitle("Password reset");
                progressDialog2.setMessage("Sending reset link, please wait....");
                progressDialog2.show();
                resetDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //extract the email and check if its valid/exists in database then send link else display an error

                        //extract the entered email
                        String mail = resetEmail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() { //if sending was successful
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressDialog2.dismiss();
                                Toast.makeText(Login.this,"Link sent to "+resetEmail.getText()+". Please click on the link to proceed",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {       //if sending failed
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(Login.this,"Link not sent! "+resetEmail.getText()+". Error(s) incurred "+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });//end of Yes

                //Cancel button
                resetDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //close the alert dialog
                        Toast.makeText(Login.this,"Reset cancelled",Toast.LENGTH_SHORT).show();
                    }
                });
                resetDialog.create().show(); //create it and show
            }
        });

    }//end of oncreate



    //log user in
    private void logUser() {
        String usermaillog = email.getText().toString();
        String passwrdlog = password.getText().toString();

        //check if the email and password fields are empty, if empty, show error
        if (TextUtils.isEmpty(usermaillog)){
            email.setError("Email cannot be empty!");
            email.requestFocus();
        }
        else if (TextUtils.isEmpty(passwrdlog)){
            password.setError("Password cannot be empty!");
            password.requestFocus();
        }
        else{
            progressDialog.setTitle("User Login");
            progressDialog.setMessage("A moment please...");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(usermaillog,passwrdlog).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        //dismiss progress dialog
                        progressDialog.dismiss();

                        //check email verification
                        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                        if (user.isEmailVerified()){
                            Toast.makeText(Login.this,"Log in successful, welcome "+email.getText(),Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this,WelcomeActivity.class)); //open Welcome activity if login was successful
                    }
                        else{
                            user.sendEmailVerification();
                            Toast.makeText(Login.this,"We have a sent an activation link to your email, please click it to activate your email",Toast.LENGTH_LONG).show();
                        }


                    }
                    else {
                        progressDialog.dismiss();

                        Toast.makeText(Login.this,"Log in Error please try again "+task.getException().getMessage(),Toast.LENGTH_LONG).show(); //show toast on error and also the error
                    }

                }
            });
        }
    }


    //firebase auth listener start and stop
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.addAuthStateListener(firebaseAuthListener);

    }
}