package com.example.adopcanapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class CatalogoMascotasFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Mascotas> mascotasList;
    private Adapter adapter;
    String url = "https://adopcan.000webhostapp.com/consultarRegistroMascotasjson.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalogo_mascotas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inicializa la lista de mascotas
        mascotasList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.vista_catalogo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Inicializa el adaptador
        adapter = new Adapter(getContext(), mascotasList);
        recyclerView.setAdapter(adapter);
        // Carga las imágenes (y datos) de la red
        cargarImagen();
    }

    private void cargarImagen() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("Catalogo_Mascotas", "Response received: " + response);
                            JSONObject root = new JSONObject(response);
                            JSONArray array = root.getJSONArray("mascotas");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject mascota = array.getJSONObject(i);

                                Log.d("Catalogo_Mascotas", "Processing JSON Object: " + mascota.toString());

                                mascotasList.add(new Mascotas(
                                        mascota.getInt("id"),
                                        mascota.getString("nombre"),
                                        mascota.getInt("edad"),
                                        mascota.getString("sexo"),
                                        mascota.getString("talla"),
                                        mascota.getString("caracter"),
                                        mascota.getDouble("peso"),
                                        mascota.getString("tipo_mascota"),
                                        mascota.getString("raza"),
                                        mascota.getString("gustos"),
                                        mascota.getString("imagen")
                                ));
                            }

                            // Notifica al adaptador que los datos han cambiado
                            adapter.notifyDataSetChanged();
                           // recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error al procesar los datos: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Catalogo_Mascotas", "Error en la petición: " + error.toString());
                Toast.makeText(getContext(), "Error en la petición: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Asegúrate de que el contexto no sea null
        if (getContext() != null) {
            Volley.newRequestQueue(getContext()).add(stringRequest);
        } else {
            Log.e("Catalogo_Mascotas", "Contexto es null");
            Toast.makeText(getContext(), "Contexto es null", Toast.LENGTH_SHORT).show();
        }
    }
}
