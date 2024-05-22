package com.example.adopcan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText  campo_correo,campo_contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button iniciar_sesiones = findViewById(R.id.iniciar_sesion);
        campo_correo = findViewById(R.id.correo);
        campo_contrasenia= findViewById(R.id.password);
        final TextView registrarse = findViewById(R.id.registrarse);

        iniciar_sesiones.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (validar()) {
                    Toast.makeText(Login.this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Prueba.class));
                    finish();
                }

            }
        });
       // etiqueta de link personas que no tienen cuenta
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegistroUsuarios.class));
            }
        });
    }



public boolean validar() {
    boolean retorno = true;
    String entrada_correo = campo_correo.getText().toString();
    String entrada_contrasenia = campo_contrasenia.getText().toString();

    String regexCorreo = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|hotmail\\.com|@outllok\\.com)$";
    String regexContrasenia = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$";



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
