package com.example.adopcanapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText campo_correo, campo_contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        final Button iniciar_sesiones = findViewById(R.id.iniciar_sesion);
        campo_correo = findViewById(R.id.correo);
        campo_contrasenia = findViewById(R.id.password);
        final TextView registrarse = findViewById(R.id.registrarse);


        iniciar_sesiones.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                if (validar()) {
//                    Toast.makeText(LoginActivity.this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(LoginActivity.this, MenuActivity.class));
//                    finish();
//                }
                validarUsuario("https://adopcan.000webhostapp.com/adopcion_login.php");

                validar();


            }
        });
        // etiqueta de link personas que no tienen cuenta
        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistroUsuariosActivity.class));

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

    private void validarUsuario(String URL) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Verifica si la respuesta contiene "exito" para determinar que el inicio de sesión fue exitoso
                if (!response.isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", campo_correo.getText().toString());
                parametros.put("contrasenia", campo_contrasenia.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}