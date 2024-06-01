package com.example.adopcanapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PerfilFragment extends Fragment {
    private Usuario usuario;
    private Mascotas mascota;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener los datos del usuario y la mascota del Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            usuario = (Usuario) bundle.getSerializable("usuario");
            mascota = (Mascotas) bundle.getSerializable("mascota");
        }

        // Mostrar los datos en los TextViews
        if (usuario != null) {
            TextView nombreTextView = view.findViewById(R.id.nombreUsuario);
            nombreTextView.setText("Nombre de usuario: " + usuario.getNombre_completo());
        }
        if (mascota != null) {
            TextView correoTextView = view.findViewById(R.id.correoUsuario);
            correoTextView.setText("Correo: " + mascota.getId());
        }
    }
}
