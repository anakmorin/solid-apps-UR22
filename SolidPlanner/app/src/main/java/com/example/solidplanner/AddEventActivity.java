package com.example.solidplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText nombreEvento, fechadesde, horadesde, fechahasta, horahasta;
    private EditText descripcion;

    private Button guardar,cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        nombreEvento=(EditText) findViewById(R.id.nameEvent);
        descripcion=(EditText) findViewById(R.id.descriptionEvent);
        fechadesde=(EditText) findViewById(R.id.beginEvent);
        fechahasta=(EditText) findViewById(R.id.endEvent);
        horadesde=(EditText) findViewById(R.id.beginHour);
        horahasta=(EditText) findViewById(R.id.endHour);

        guardar=(Button) findViewById(R.id.addButton);
        cancelar=(Button) findViewById(R.id.cancelButton);
        guardar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        int dia,mes,año;
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        año = bundle.getInt("año");

        fechadesde.setText(dia+" - "+mes+" - "+año);
        fechahasta.setText(dia+" - "+mes+" - "+año);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==guardar.getId()){
            BaseDeDatos BD = new BaseDeDatos(getApplication(),"Agenda",null,1);
            SQLiteDatabase DB = BD.getWritableDatabase();

            String sql = "insert into eventos"+
                    "(nombreEvento,fechadesde,horadesde,fechahasta,horahasta,descripcion) values('"+
                    nombreEvento.getText()+
                    "','" + fechadesde.getText()+
                    "','" + horadesde.getText()+
                    "','" + fechahasta.getText()+
                    "','" + horahasta.getText()+
                    "','" + descripcion.getText()+
                    "')";
            try{
                DB.execSQL(sql);

                nombreEvento.setText("");
                fechadesde.setText("");
                horadesde.setText("");
                fechahasta.setText("");
                horahasta.setText("");
                descripcion.setText("");
            }catch (Exception e){
                Toast.makeText(getApplication(),"Error"+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
            this.finish();
        }else{
            this.finish();
            return;
        }
    }
}