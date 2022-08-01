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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener{

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private Button logOut;

    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mAuth = FirebaseAuth.getInstance();
        logOut = findViewById(R.id.logout);
        user = mAuth.getCurrentUser();

        calendarView = (CalendarView) findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(this);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalirApp();
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
        // Check if user is signed in (non-null) and update UI accordingly.
        // updateUI(currentUser);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[3];
        items[0]="Agregar evento";
        items[1]="Editar evento";
        items[2]="Borrar evento";

        final int dia, mes, año;
        dia = i;
        mes = i1;
        año = i2;

        builder.setTitle("Selecciona una tarea").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    Intent intent = new Intent(getApplication(),AddEventActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("día",dia);
                    bundle.putInt("mes",mes);
                    bundle.putInt("año",año);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else if(i==1){
                    Intent intent = new Intent(getApplication(),ViewEventActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("día",dia);
                    bundle.putInt("mes",mes);
                    bundle.putInt("año",año);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    return;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}