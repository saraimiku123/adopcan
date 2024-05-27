package com.example.adopcanapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
public class RegistroUsuariosActivity extends AppCompatActivity {

    EditText campo_nombre, campo_telefono, campo_correo, campo_contrasenia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistro_usuarios);
        final Button crearuser = findViewById(R.id.crearuser);
        campo_nombre = findViewById(R.id.namecrear);
        campo_telefono = findViewById(R.id.telefono);
        campo_correo = findViewById(R.id.correo);
        campo_contrasenia = findViewById(R.id.contraseniatext);


        crearuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validar()) {
                    Toast.makeText(RegistroUsuariosActivity.this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegistroUsuariosActivity.this, MenuActivity.class));
                    finish();
                }

            }
        });
    }

    public boolean validar() {
        boolean retorno = true;
        String entrada_nombre = campo_nombre.getText().toString();
        String entrada_telefono = campo_telefono.getText().toString();
        String entrada_correo = campo_correo.getText().toString();
        String entrada_contrasenia = campo_contrasenia.getText().toString();

        String regexCorreo = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|hotmail\\.com|@outllok\\.com)$";
        String regexTelefono = "^\\+\\d{1,3}\\d{10}$";
        String regexContrasenia = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$";

        if (entrada_nombre.isEmpty()) {
            campo_nombre.setError("El nombre no puede quedar vacío");
            retorno = false;
        }
        if (entrada_telefono.isEmpty()) {
            campo_telefono.setError("El teléfono no puede quedar vacío");
            retorno = false;
        } else if (!entrada_telefono.matches(regexTelefono)) {
            campo_telefono.setError("El teléfono debe incluir lada (1-3 dígitos) seguida de un número (10 dígitos)\"");

        }
        if (entrada_correo.isEmpty()) {
            campo_correo.setError("El correo no puede quedar vacío");
            retorno = false;
        } else if (!entrada_correo.matches(regexCorreo)) {
            campo_correo.setError("El correo debe contener letras, números y caracteres especiales comunes, y terminar en '@.com'");
            retorno = false;
        }
        if (entrada_contrasenia.isEmpty()) {
            campo_contrasenia.setError("La contraseña no puede quedar vacía");
            retorno = false;
        } else if (!entrada_contrasenia.matches(regexContrasenia)) {
            campo_contrasenia.setError("La contraseña debe tener al menos 8 caracteres, incluyendo letras mayúsculas, minúsculas, números y caracteres especiales");
            retorno = false;

        }

        return retorno;
    }
}