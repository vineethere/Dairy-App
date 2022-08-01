package com.example.dairyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.core.motion.utils.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.concurrent.Executor;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button Login;
    private EditText Username;
    private EditText Password;
    private Button Register;

    String temp=" ";
    String uid=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        Username=(EditText) findViewById(R.id.Username);
        Password=(EditText) findViewById(R.id.Password);
        Login=findViewById(R.id.Login);
        Register=findViewById(R.id.RegisterUser);

        mAuth = FirebaseAuth.getInstance();

        //-------------------------
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    startActivity(new Intent(Login.this, Registration.class));
            }
        });
        //--------------------------





        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=Username.getText().toString();
                String password=Password.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this, "email cant be empty", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "password cant be empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                       passdata();
                                    }
                                    else {
                                        Toast.makeText(Login.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                }


                                private void passdata() {
                                    FirebaseUser currentUser= mAuth.getCurrentUser();
                                    if(currentUser != null){
                                        temp = currentUser.getEmail();
                                        uid=currentUser.getUid();
                                    }
                                    else {
                                        temp = "Try again";
                                        uid = "Try again";
                                    }

                                    Intent intent=new Intent(Login.this,Query1.class);
                                    intent.putExtra("message1",temp);
                                    intent.putExtra("message2",uid);
                                    startActivity(intent);finish();
                                }
                            });
                }



            }

        });



    }
}