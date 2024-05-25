package com.example.adopcan;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Catalogo_MascotasFragment extends Fragment {
    private TextView textViewNombre;
    private ImageView imageView;
    private static final String TAG = "Catalogo_MascotasFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalogo__mascotas, container, false);
        textViewNombre = view.findViewById(R.id.catalogo);
        imageView = view.findViewById(R.id.catalogo_imagen);

        // Llama al método para hacer la solicitud con Volley
        Log.d(TAG, "Antes de hacer la solicitud");
        makeVolleyRequest();
        return view;
    }

    private void makeVolleyRequest() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://adopcan.000webhostapp.com/consultarRegistroMascotasjson.php";

        Log.d(TAG, "URL de la solicitud: " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "Respuesta recibida: " + response);
                        try {
                            // Suponiendo que la respuesta es un JSON con un array de mascotas
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray mascotas = jsonObject.getJSONArray("mascotas");
                            if (mascotas.length() > 1) { // Cambiado a > 1 para asegurar que haya al menos una mascota
                                JSONObject mascota = mascotas.getJSONObject(7); // Obtener la primera mascota
                                String nombre = mascota.getString("nombre");
                                String imagen = mascota.getString("imagen");

                                // Log de la URL de la imagen
                                Log.d(TAG, "Nombre de la mascota: " + nombre);
                                Log.d(TAG, "URL de la imagen: " + imagen);

                                textViewNombre.setText("Nombre: " + nombre);

                                // Usar Picasso para cargar la imagen
                                Picasso.get()
                                        .load(imagen)
                                        .into(imageView);

                                // Agrega un Toast para verificar que se está intentando cargar la imagen
                                Toast.makeText(getContext(), "Cargando imagen: " + imagen, Toast.LENGTH_LONG).show();
                            } else {
                                textViewNombre.setText("No hay mascotas disponibles");
                                Log.d(TAG, "No hay mascotas disponibles");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            textViewNombre.setText("Error al procesar la respuesta");
                            Log.e(TAG, "Error al procesar la respuesta JSON", e);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textViewNombre.setText("No se pudo realizar la solicitud");
                Log.e(TAG, "Error en la solicitud", error);
            }
        });

        // Agrega la solicitud a la cola de solicitudes
        queue.add(stringRequest);
        Log.d(TAG, "Solicitud añadida a la cola");
    }
}