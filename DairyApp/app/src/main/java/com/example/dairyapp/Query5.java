package com.example.dairyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Query5 extends AppCompatActivity {

    private Button Login;
    private EditText ans1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query5);
        Login = (Button) findViewById(R.id.Login);
        ans1 = (EditText) findViewById(R.id.ans1);
        Intent intent = getIntent();
        String email = intent.getStringExtra("message1");
        String uid = intent.getStringExtra("message2");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String todaydate = sdf.format(new Date());


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ans = ans1.getText().toString();
                if (TextUtils.isEmpty(ans))
                    Toast.makeText(Query5.this, "Please enter something", Toast.LENGTH_SHORT).show();

                else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("4");
                    ref.child(todaydate).setValue(ans);
                    Intent intent = new Intent(Query5.this, Thankyou.class);
                    intent.putExtra("message1", email);
                    intent.putExtra("message2", uid);
                    startActivity(intent);
                    finish();

                }
            }
        });

    }
}