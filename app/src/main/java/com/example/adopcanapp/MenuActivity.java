package com.example.adopcanapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.adopcanapp.databinding.ActivityMenuBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new CatalogoMascotasFragment(), null);
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment selectedFragment = null;
            Bundle bundle = null;

            if (id == R.id.tienda) {
                selectedFragment = new TiendaFragment();
            } else if (id == R.id.donaciones) {
                selectedFragment = new DonacionesFragment();
            } else if (id == R.id.catalogo) {
                selectedFragment = new CatalogoMascotasFragment();
            } else if (id == R.id.galeria) {
                selectedFragment = new GaleriaFragment();
            } else if (id == R.id.perfil) {
                selectedFragment = new PerfilFragment();
                Intent intent = getIntent();
                Usuario usuario = (Usuario) intent.getSerializableExtra("usuario");
                if (usuario != null) {
                    bundle = new Bundle();
                    bundle.putSerializable("usuario", usuario);
                }
            }

            replaceFragment(selectedFragment, bundle);
            return true;
        });

        binding.bottomNavigationView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.bottomNavigationView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                for (int i = 0; i < binding.bottomNavigationView.getMenu().size(); i++) {
                    MenuItem item = binding.bottomNavigationView.getMenu().getItem(i);
                    View view = binding.bottomNavigationView.findViewById(item.getItemId());
                    if (view != null) {
                        view.setOnLongClickListener(v -> true);
                    }
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}



