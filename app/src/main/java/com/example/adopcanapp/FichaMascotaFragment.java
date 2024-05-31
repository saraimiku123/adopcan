package com.example.adopcanapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class FichaMascotaFragment extends Fragment {

    private Mascotas mascota;
    private Usuario usuario; // Define usuario como miembro de la clase

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ficha_mascota, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mascota = (Mascotas) getArguments().getSerializable("mascota");
            usuario = (Usuario) getArguments().getSerializable("usuario"); // Obtén el usuario de los argumentos
        }

        if (mascota != null) {
            ImageView imageView = view.findViewById(R.id.imageViewDetalle);
            TextView nombreTextView = view.findViewById(R.id.nombreDetalle);
            TextView edadTextView = view.findViewById(R.id.edadDetalle);
            TextView sexoTextView = view.findViewById(R.id.sexoDetalle);
            TextView tallaTextView = view.findViewById(R.id.tallaDetalle);
            TextView caracterTextView = view.findViewById(R.id.caracterDetalle);
            TextView pesoTextView = view.findViewById(R.id.pesoDetalle);
            TextView tipoTextView = view.findViewById(R.id.tipoDetalle);
            TextView razaTextView = view.findViewById(R.id.razaDetalle);
            TextView gustosTextView = view.findViewById(R.id.gustosDetalle);
            final Button adoptarMascota = view.findViewById(R.id.botonAdoptar);

            Glide.with(getContext())
                    .load(mascota.getImagen())
                    .into(imageView);

            nombreTextView.setText("Nombre: " + mascota.getNombre());
            edadTextView.setText("Edad: " + mascota.getEdad() + " Años");
            sexoTextView.setText("Sexo: " + mascota.getSexo());
            tallaTextView.setText("Talla: " + mascota.getTalla());
            caracterTextView.setText("Caracter: " + mascota.getCaracter());
            pesoTextView.setText("Peso: " + mascota.getPeso() + " Kilos");
            tipoTextView.setText("Tipo Mascota: " + mascota.getTipo_mascota());
            razaTextView.setText("Raza: " + mascota.getRaza());
            gustosTextView.setText("Gustos: " + mascota.getGustos());

            adoptarMascota.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new FormularioAdopcionFragment();

                    // Crear un bundle para pasar los datos del usuario y la mascota seleccionada
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("usuario", usuario);
                    fragment.setArguments(bundle);

                    // Realizar la transición al fragmento de formulario de adopción
                    FragmentManager fragmentManager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }
}
