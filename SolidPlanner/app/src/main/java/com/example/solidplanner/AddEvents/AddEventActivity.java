package com.example.solidplanner.AddEvents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solidplanner.Objetos.Nota;
import com.example.solidplanner.R;
import com.example.solidplanner.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    EditText nombreEvento, descripcion;
    TextView Fecha;
    Button btn_calendario, btn_hora, guardar, cancelar;

    int dia,mes,anio;

    FirebaseAuth mAuth;
    FirebaseUser user;

    DatabaseReference bd_firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        guardar = findViewById(R.id.addButton);
        cancelar = findViewById(R.id.cancelButton);

        guardar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        iniciarVariables();

        btn_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int AnioSeleccionado, int MesSeleccionado, int DiaSeleccionado) {

                        String diaFormateado, mesFormateado;

                        if (DiaSeleccionado < 10){
                            diaFormateado = "0"+String.valueOf(DiaSeleccionado);
                        }else {
                            diaFormateado = String.valueOf(DiaSeleccionado);
                        }

                        int Mes = MesSeleccionado + 1;

                        if (Mes < 10){
                            mesFormateado = "0"+String.valueOf(Mes);
                        }else {
                            mesFormateado = String.valueOf(Mes);
                        }

                        Fecha.setText(diaFormateado + "/" + mesFormateado + "/"+ AnioSeleccionado);

                    }
                }
                ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        btn_calendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();

                dia = calendar.get(Calendar.DAY_OF_MONTH);
                mes = calendar.get(Calendar.MONTH);
                anio = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int AnioSeleccionado, int MesSeleccionado, int DiaSeleccionado) {

                        String diaFormateado, mesFormateado;

                        if (DiaSeleccionado < 10){
                            diaFormateado = "0"+String.valueOf(DiaSeleccionado);
                        }else {
                            diaFormateado = String.valueOf(DiaSeleccionado);
                        }

                        int Mes = MesSeleccionado + 1;

                        if (Mes < 10){
                            mesFormateado = "0"+String.valueOf(Mes);
                        }else {
                            mesFormateado = String.valueOf(Mes);
                        }

                        Fecha.setText(diaFormateado + "/" + mesFormateado + "/"+ AnioSeleccionado);

                    }
                }
                        ,anio,mes,dia);
                datePickerDialog.show();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEventActivity.this, UserActivity.class));
                Toast.makeText(getApplicationContext(), "Se ha cancelado la acción", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void iniciarVariables() {
        nombreEvento = findViewById(R.id.nameEvent);
        descripcion = findViewById(R.id.descriptionEvent);
        btn_calendario = findViewById(R.id.Btn_Calendario);

        btn_hora = findViewById(R.id.Btn_Hora);
        Fecha = findViewById(R.id.Fecha);

        bd_firebase = FirebaseDatabase.getInstance().getReference();
    }

    private void agregarNota(){
        String titulo = nombreEvento.getText().toString();
        String Descripcion = descripcion.getText().toString();
        String fecha = Fecha.getText().toString();
        String id_nota = bd_firebase.push().getKey();

        //Validar datos
        if (!titulo.equals("") && !Descripcion.equals("") && ! fecha.equals("")){

            Nota nota = new Nota(id_nota, titulo, Descripcion, fecha);


            String Nombre_BD = "Notas_Publicadas";

            assert id_nota != null;
            bd_firebase.child(Nombre_BD).child(id_nota).setValue(nota);

            Toast.makeText(this, "Se ha agregado la nota exitosamente", Toast.LENGTH_SHORT).show();
            onBackPressed();

        }
        else {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_agregar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Agregar_Nota_BD:
                agregarNota();
                Toast.makeText(this, "Nota agregada con éxito!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View view) {

    }
}