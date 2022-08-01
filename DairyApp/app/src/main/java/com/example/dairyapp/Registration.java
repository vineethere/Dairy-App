package com.example.dairyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Registration extends AppCompatActivity {
    public Button Register;
    private EditText username;
    private EditText Password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Register=findViewById(R.id.Login);
        username=findViewById(R.id.Username);
        Password=findViewById(R.id.Password);

        auth=FirebaseAuth.getInstance();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = username.getText().toString();
                String password = Password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter email!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(),
                                    "Please enter password!!",
                                    Toast.LENGTH_LONG)
                            .show();
                    return;
                }


                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //------------------------------
                            List<String>list=new ArrayList<>();
                            list.add("Q1");
                            list.add("Q2");
                            list.add("Q3");
                            list.add("Q4");
                            list.add("Q5");
                            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            DatabaseReference userDataRef = FirebaseDatabase.getInstance().getReference("Users").child(uid);
                            userDataRef.setValue(list);
                            Toast.makeText(Registration.this, "Everything done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Registration.this,Login.class));
                            finish();

                            //-------------------------------------------------------------------------

                        } else {
                           Toast.makeText(Registration.this, "not registered", Toast.LENGTH_SHORT).show();
                        }
                    }


                });

            }
        });

    }
}