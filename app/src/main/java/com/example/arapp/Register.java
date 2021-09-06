package com.example.arapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {
    private TextView gotologin;
    //authenticate registration
    private Button btnregister;
    private EditText edtemail,fullName;
    private EditText edtpassword1;
    private EditText edtpassword2;



    //firebase authentication class
    FirebaseAuth mAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initialization
        gotologin=findViewById(R.id.txtGotologin);

        btnregister=findViewById(R.id.button);
        edtemail=findViewById(R.id.edtEmail);
        fullName=findViewById(R.id.editTextTextPersonName);
        edtpassword1=findViewById(R.id.password1);
        edtpassword2=findViewById(R.id.password2);


        mAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        //registration button onclick method
        btnregister.setOnClickListener(view ->{
            reGister();
        });


    }//end of oncreate


    //Registration method
    private void reGister() {
        String fname = fullName.getText().toString();
        String userMail = edtemail.getText().toString();
        String password1 = edtpassword1.getText().toString();
        String password2 = edtpassword2.getText().toString();

        //check if the email and password fields are empty, if empty, show error
        if (TextUtils.isEmpty(userMail)){
            edtemail.setError("Email cannot be empty!");
            edtemail.requestFocus();
        }
        else if (TextUtils.isEmpty(password1)){
            edtpassword1.setError("Password cannot be empty!");
            edtpassword1.requestFocus();
        }
        //if passwords don't match
        else if(!password1.equals(password2)){
            edtpassword1.setError("Password did not match, please try again!");
            edtpassword1.setText("");
            edtpassword2.setText("");
            edtpassword1.requestFocus();
        }

        else{
            progressDialog.setTitle("Registration");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(userMail,password2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //check if the creation task was successful
                    if (task.isSuccessful()){
                    User user=new User(fname,userMail);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    //dismiss loading bar
                                    progressDialog.dismiss();
                                    //display a toast once successful
                                    Toast.makeText(Register.this,"Registered successfully",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(Register.this,Login.class);
                                    startActivity(intent);
                                    finish();
                                }else {
                                    //dismiss loading bar
                                    progressDialog.dismiss();
                                    Toast.makeText(Register.this,"User not registered on database, please try again "+task.getException().getMessage(),Toast.LENGTH_SHORT).show(); //show toast on error and also the error
                                }
                            }
                        });


                    }
                    else{
                        //dismiss loading bar
                        progressDialog.dismiss();
                        Toast.makeText(Register.this,"Registration Error, please try again "+task.getException().getMessage(),Toast.LENGTH_SHORT).show(); //show toast on error and also the error
                    }
                }
            });
        }
    }
}