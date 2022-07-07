package com.example.solidplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void InlogIn(View view){
        Intent i = new Intent(this, LogInActivity.class);
        startActivity(i);
    }

    public void InsignIn(View view){
        Intent i = new Intent(this, SignInActivity.class);
        startActivity(i);
    }

    public void InlogOut(View view){
        Intent i = new Intent(getApplicationContext(), UserActivity.class);
        startActivity(i);
    }
}