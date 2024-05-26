package com.example.adopcan;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.adopcan.databinding.ActivityPruebaBinding;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;


public class Prueba extends AppCompatActivity {
   ActivityPruebaBinding binding;
    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPruebaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new Catalogo_MascotasFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {


            int id= item.getItemId();
            if(id==R.id.tienda){
                       replaceFragment(new TiendaFragment());

            }
            else if(id==R.id.donaciones){
                replaceFragment(new DonacionesFragment());

            }
            else if(id==R.id.catalogo){
                replaceFragment(new Catalogo_MascotasFragment());

            }

            else if(id==R.id.galeria){
                replaceFragment(new GaleriaFragment());

            }

            else if(id==R.id.perfil){
                replaceFragment(new PerfilFragment());


            }





            return true;
        });


        binding.bottomNavigationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Quitar el listener para evitar múltiples llamadas
                binding.bottomNavigationView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                for (int i = 0; i < binding.bottomNavigationView.getMenu().size(); i++) {
                    MenuItem item = binding.bottomNavigationView.getMenu().getItem(i);
                    View view = binding.bottomNavigationView.findViewById(item.getItemId());
                    if (view != null) {
                        view.setOnLongClickListener(v -> {
                            binding.bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);

                            return true;
                        });
                    }
                }
            }
        });
    }




    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);


        fragmentTransaction.commit();
    }
}



