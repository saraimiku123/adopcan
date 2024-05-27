package com.example.adopcan;

import android.os.Bundle;
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

public class Catalogo_MascotasFragment extends Fragment {

    RecyclerView recyclerView;
    List<Mascotas> mascotasList;
    String url = "https://adopcan.000webhostapp.com/consultarRegistroMascotasjson.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalogo__mascotas, container, false);

        recyclerView = view.findViewById(R.id.vista_catalogo);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mascotasList = new ArrayList<>();

        cargarImagen();
        return view;
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
                                JSONObject Mascotas = array.getJSONObject(i);

                                Log.d("Catalogo_Mascotas", "Processing JSON Object: " + Mascotas.toString());

                                mascotasList.add(new Mascotas(
                                        Mascotas.getInt("id"),
                                        Mascotas.getString("nombre"),
                                        Mascotas.getInt("edad"),
                                        Mascotas.getString("sexo"),
                                        Mascotas.getString("talla"),
                                        Mascotas.getString("caracter"),
                                        Mascotas.getDouble("peso"),
                                        Mascotas.getString("tipo_mascota"),
                                        Mascotas.getString("raza"),
                                        Mascotas.getString("gustos"),
                                        Mascotas.getString("imagen")
                                ));
                            }
                            Adapter adapter = new Adapter(getContext(), Catalogo_MascotasFragment.this.mascotasList);
                            recyclerView.setAdapter(adapter);
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