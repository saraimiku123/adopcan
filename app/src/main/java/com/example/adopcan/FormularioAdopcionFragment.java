package com.example.adopcan;

import android.content.ContextParams;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;

import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class FormularioAdopcionFragment extends Fragment {


    private Spinner estado_civil1;
    private  Spinner jardines  ;
    private  Spinner mejoras_hogar;

    private Spinner disponibilidad_tiempo;

    private Spinner ayudante_cuidador;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragmen


    RequestQueue queue = Volley.newRequestQueue(getContext());



        View view= inflater.inflate(R.layout.fragment_formulario_adopcion, container, false);
         estado_civil1= view.findViewById(R.id.estado_civil);
         jardines= view.findViewById(R.id.jardin);
         mejoras_hogar= view.findViewById(R.id.adecuaciones);
         disponibilidad_tiempo=view.findViewById(R.id.tiempo);
         ayudante_cuidador = view.findViewById(R.id.ayudante);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        estado_civil1.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapterEstadoCivil = ArrayAdapter.createFromResource(getContext(),
                R.array.opciones, android.R.layout.simple_spinner_item);
        adapterEstadoCivil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jardines.setAdapter(adapterEstadoCivil);

        ArrayAdapter<CharSequence> adapterMejorasCasa= ArrayAdapter.createFromResource(getContext(),
                R.array.opciones, android.R.layout.simple_spinner_item);
        adapterMejorasCasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mejoras_hogar.setAdapter(adapterMejorasCasa);

        ArrayAdapter<CharSequence> adapterDisponibilidad= ArrayAdapter.createFromResource(getContext(),
                R.array.opciones, android.R.layout.simple_spinner_item);
        adapterDisponibilidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disponibilidad_tiempo.setAdapter(adapterDisponibilidad);

        ArrayAdapter<CharSequence> adapterAyudanteCuidador= ArrayAdapter.createFromResource(getContext(),
                R.array.opciones, android.R.layout.simple_spinner_item);
        adapterAyudanteCuidador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ayudante_cuidador.setAdapter(adapterAyudanteCuidador);








        return view;



    }


}