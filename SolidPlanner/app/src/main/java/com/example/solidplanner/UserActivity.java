package com.example.solidplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.solidplanner.AddEvents.AddEventActivity;
import com.example.solidplanner.ShowEvents.ViewEventActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity{

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private Button logOut, addEvent, showEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        logOut = findViewById(R.id.logout);
        addEvent = findViewById(R.id.addButton);
        showEvents = findViewById(R.id.showButton);
        user = mAuth.getCurrentUser();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalirApp();
            }
        });
        
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, AddEventActivity.class));
                Toast.makeText(getApplicationContext(), "Agregar Evento", Toast.LENGTH_SHORT).show();
            }
        });
        
        showEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserActivity.this, ViewEventActivity.class));
                Toast.makeText(getApplicationContext(), "Ver Eventos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void SalirApp() {
        mAuth.signOut();
        startActivity(new Intent(UserActivity.this,MainActivity.class));
        Toast.makeText(getApplicationContext(), "¡Cerraste sesión exitosamente!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}