package com.example.adopcanapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;


import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.PlayerViewHolder> {

    private Context mCtx;
    private List<Mascotas> mascotasList;

    public Adapter(Context mCtx, List<Mascotas> mascotasList) {
        this.mCtx = mCtx;
        this.mascotasList = mascotasList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.list_layout, viewGroup, false);
        return new PlayerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Mascotas mascotas = mascotasList.get(position);
        Glide.with(mCtx)
                .load(mascotas.getImagen())
                .into(holder.imgMascota);
        holder.tvnombre.setText("Nombre: " + mascotas.getNombre());
        holder.tvedad.setText("Edad: " + mascotas.getEdad());
        holder.tvsexo.setText("Sexo: " + mascotas.getSexo());

        holder.detalles.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Fragment fragment = new FichaMascotaFragment();

               // Crear un bundle para pasar los datos de la mascota seleccionada
               Bundle bundle = new Bundle();
               bundle.putSerializable("mascota", mascotas);
               fragment.setArguments(bundle);

               // Realizar la transición al fragmento de detalles
               FragmentManager fragmentManager = ((FragmentActivity) mCtx).getSupportFragmentManager();
               fragmentManager.beginTransaction()
                       .replace(R.id.frame_layout, fragment)
                       .addToBackStack(null)
                       .commit();

           }
       });



    }

    @Override
    public int getItemCount() {
        return mascotasList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {
        TextView tvnombre, tvedad, tvsexo;
        ImageView imgMascota;
        CardView recCard;
        Button detalles;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnombre = itemView.findViewById(R.id.nombre_mascota);
            tvedad = itemView.findViewById(R.id.edad_mascota);
            tvsexo = itemView.findViewById(R.id.sexo_mascota);
            imgMascota = itemView.findViewById(R.id.imagen_mascota);
            detalles = itemView.findViewById(R.id.boton_vermas);

        }
    }
}
