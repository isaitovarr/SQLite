package com.example.ejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private  EditText et_codigo, et_descripcion, et_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_codigo = (EditText)findViewById(R.id.txt_codigo);
        et_descripcion = (EditText)findViewById(R.id.txt_descripcion);
        et_precio = (EditText)findViewById((R.id.txt_precio));
    }

    //Metodo altas - registro
    public void Registro(View view){
        Administrador admin = new Administrador(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(codigo.isEmpty() && descripcion.isEmpty() && precio.isEmpty()){
            Toast.makeText(this, "Debes llenar los campos",Toast.LENGTH_SHORT).show();
        }
        else{
            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            BaseDeDatos.insert("articulos",null, registro);

            BaseDeDatos.close();
            Toast.makeText(this, "Registro completado",Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo consulta - buscar
    public void Buscar(View view){
        Administrador admin = new Administrador(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(codigo.isEmpty()){
            Toast.makeText(this, "El codigo esta vacio",Toast.LENGTH_SHORT).show();
        }
        else{
            Cursor fila = BaseDeDatos.rawQuery("select descripcion, precio from articulos where codigo =" + codigo,null);

            if(fila.moveToFirst()){
                et_descripcion.setText(fila.getString(0));
                et_precio.setText(fila.getString(1));
                BaseDeDatos.close();
            }
            else{
                Toast.makeText(this, "No se encontraron coincidencias",Toast.LENGTH_SHORT).show();
                BaseDeDatos.close();
            }
        }
    }

    //Metodo de bajas - eliminar
    public void Eliminar(View view){
        Administrador admin = new Administrador(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();

        if(codigo.isEmpty()){
            Toast.makeText(this, "El codigo esta vacio",Toast.LENGTH_SHORT).show();
        }
        else{
            int cantidad = BaseDeDatos.delete("articulos","codigo=" + codigo,null);
            BaseDeDatos.close();

            et_codigo.setText("");
            et_descripcion.setText("");
            et_precio.setText("");

            if( cantidad == 1){
                Toast.makeText(this, "Articulo eliminado exitosamente",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "El articulo no existe",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Metodo para modificar
    public void Modificar(View view){
        Administrador admin = new Administrador(this,"administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String codigo = et_codigo.getText().toString();
        String descripcion = et_descripcion.getText().toString();
        String precio = et_precio.getText().toString();

        if(codigo.isEmpty() && descripcion.isEmpty() && precio.isEmpty()){
            Toast.makeText(this, "Debes llenar los datos",Toast.LENGTH_SHORT).show();
        }
        else{
            ContentValues registro = new ContentValues();

            registro.put("codigo",codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);

            int cantidad = BaseDeDatos.update("articulos", registro,"codigo=" + codigo,null);
            BaseDeDatos.close();

            if( cantidad == 1){
                Toast.makeText(this, "Articulo modificado exitosamente",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "El articulo no existe",Toast.LENGTH_SHORT).show();
            }
        }
    }
}