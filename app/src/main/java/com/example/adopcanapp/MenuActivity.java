package com.example.adopcanapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.adopcanapp.databinding.ActivityMenuBinding;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MenuActivity extends AppCompatActivity {

     ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        replaceFragment(new CatalogoMascotasFragment());
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
                replaceFragment(new CatalogoMascotasFragment());

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



