package com.example.solidplanner;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private EditText nombre;
    private EditText email;
    private EditText password;
    private EditText password2;

    Button SignIn2;
    TextView TengoCuenta;

    String nombres = " " ,correo = " ", pwd = " " , pwd2 = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Registrar");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        nombre = findViewById(R.id.nombre);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        SignIn2 = findViewById(R.id.SignIn2);
        TengoCuenta = findViewById(R.id.TengoCuenta);

        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Espere por favor");
        progressDialog.setCanceledOnTouchOutside(false);

        SignIn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                ValidarDatos();
            }
        });

        TengoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, LogInActivity.class));
            }
        });
    }

    private void ValidarDatos() {
        nombres = nombre.getText().toString();
        correo = email.getText().toString();
        pwd = password.getText().toString();
        pwd2 = password2.getText().toString();

        if (TextUtils.isEmpty(nombres)){
            Toast.makeText(this, "Ingrese nombre", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
            Toast.makeText(this, "Ingrese correo", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(pwd)){
            Toast.makeText(this, "Ingrese contraseña", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(pwd2)){
            Toast.makeText(this, "Confirme contraseña", Toast.LENGTH_SHORT).show();

        }
        else if (!pwd.equals(pwd2)){
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
        else {
            CrearCuenta();
        }
    }

    private void CrearCuenta() {
        progressDialog.setMessage("Creando su cuenta...");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(correo, pwd)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        GuardarInformacion();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignInActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void GuardarInformacion() {
        progressDialog.setMessage("Guardando su información");
        progressDialog.dismiss();

        String uid = mAuth.getUid();

        HashMap<String, String> Datos = new HashMap<>();
        /*DATOS DEL USUARIO*/
        Datos.put("uid",  uid);
        Datos.put("correo", correo);
        Datos.put("nombres", nombres);
        Datos.put("password", pwd);

        Datos.put("apellidos", "");
        Datos.put("edad","");
        Datos.put("telefono","");
        Datos.put("domicilio","");
        Datos.put("universidad","");
        Datos.put("profesion","");
        Datos.put("fecha_de_nacimiento", "");
        Datos.put("imagen_perfil","");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios");
        databaseReference.child(uid)
                .setValue(Datos)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignInActivity.this, UserActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SignInActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}