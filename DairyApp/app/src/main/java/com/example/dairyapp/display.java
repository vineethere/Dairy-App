package com.example.dairyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class display extends AppCompatActivity {

    private EditText Date;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    private TextView show;
    private Button display;
    private EditText query;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent intent=getIntent();
        String email= intent.getStringExtra("message1");
        String uid = intent.getStringExtra("message2");
        query=findViewById(R.id.query);
        Date=findViewById(R.id.Date);
        show=findViewById(R.id.show);
        display=findViewById(R.id.display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a =query.getText ().toString ();

                String date= Date.getText().toString();
                if(TextUtils.isEmpty(date)){
                    Toast.makeText(display.this, "Please enter something", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(a)){
                    Toast.makeText(display.this, "Pls enter query number", Toast.LENGTH_SHORT).show();
                }
                else{
                    DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child(uid).child(a).child(date);
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                            String value = datasnapshot.getValue().toString();
                             show.setText(value);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(display.this, "Fail to get data", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }

}