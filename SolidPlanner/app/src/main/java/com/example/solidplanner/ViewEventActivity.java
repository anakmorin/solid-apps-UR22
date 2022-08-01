package com.example.solidplanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewEventActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {

    private SQLiteDatabase db;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);

        listView=(ListView) findViewById(R.id.viewEvents);
        listView.setOnItemLongClickListener(this);

        Bundle bundle = getIntent().getExtras();
        int dia,mes,año;
        dia = bundle.getInt("dia");
        mes = bundle.getInt("mes");
        año = bundle.getInt("año");
        String cadena=dia+" - "+mes+" - "+año;

        BaseDeDatos bd = new BaseDeDatos(getApplicationContext(),"Agenda",null,1);
        db = bd.getReadableDatabase();

        String sql = "select * from eventos where fechadesde='"+cadena+"'";
        Cursor c;

        String nombre,fechadesde,horadesde,fechahasta,horahasta,descripcion;
        try{
            c=db.rawQuery(sql,null);
            arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1);
            if(c.moveToFirst()){
                do {
                    nombre=c.getString(1);
                    fechadesde=c.getString(2);
                    horadesde=c.getString(3);
                    fechahasta=c.getString(4);
                    horahasta=c.getString(5);
                    descripcion=c.getString(6);
                    arrayAdapter.add(nombre+", "+fechadesde+", "+horadesde+", "+fechahasta+", "+horahasta+", "+descripcion);
                }while (c.moveToFirst());
                listView.setAdapter(arrayAdapter);
            }else{
                this.finish();
            }
        }catch (Exception e) {
            Toast.makeText(getApplication(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    private void eliminarEvento(String dato){
        String []datos = dato.split(", ");
        String sql="delete from eventos where nombreEvento='"+datos[0]+"' and" +
                " fechadesde='"+datos[1]+"' and horadesde='"+datos[2]+"' and "+
                " fechahasta='"+datos[3]+"' and horahasta='"+datos[4]+"' and "+
                " descripcion='"+datos[5]+"'";
        try {
            arrayAdapter.remove(dato);
            listView.setAdapter(arrayAdapter);
            db.execSQL(sql);
            Toast.makeText(getApplication(),"Evento eliminado",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplication(),"Error: "+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence []items = new CharSequence[2];
        items[0]="Eliminar evento";
        items[1]="Cancelar";
        builder.setTitle("Eliminar evento").setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    eliminarEvento(adapterView.getItemAtPosition(i).toString());
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return false;
    }
}