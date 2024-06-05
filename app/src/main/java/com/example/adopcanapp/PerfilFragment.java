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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            usuario = (Usuario) getArguments().getSerializable("usuario");
        }

        if (usuario != null) {
            TextView nombreTextView = view.findViewById(R.id.nombreUsuario);
            nombreTextView.setText("Nombre de usuario: " + usuario.getNombre_completo());

            TextView correoTextView = view.findViewById(R.id.correoUsuario);
            correoTextView.setText("Correo: " + usuario.getCorreo());

        }

    }
}
