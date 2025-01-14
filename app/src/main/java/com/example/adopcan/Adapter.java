package com.example.adopcan;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, viewGroup, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Mascotas mascotas = mascotasList.get(position);
        Glide.with(mCtx)
                .load(mascotas.getImagen())
                .into(holder.imgMascota);
        holder.tvnombre.setText("Nombre: "+mascotas.getNombre());
        holder.tvedad.setText("Edad: "+mascotas.getEdad());
        holder.tvsexo.setText("Sexo"+mascotas.getSexo());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mCtx,"mascota"+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mCtx, Ficha_MascotaFragment.class);
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


        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvnombre = itemView.findViewById(R.id.nombre_mascota);
            tvedad = itemView.findViewById(R.id.edad_mascota);
            tvsexo = itemView.findViewById(R.id.sexo_mascota);
            imgMascota = itemView.findViewById(R.id.imagen_mascota);
            recCard = itemView.findViewById(R.id.recCard);


        }
    }
}