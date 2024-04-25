package com.ads.johnecommerce.activity.loja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.databinding.ActivityMainEmpresaBinding;
import com.ads.johnecommerce.databinding.ActivityMainUsuarioBinding;

public class MainActivityEmpresa extends AppCompatActivity {

    private ActivityMainEmpresaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainEmpresaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

       /* NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nv_host_fragment);
        NavController navController = navHostFragment.getNavController();

        Navigation.setViewNavController(binding.);*/

    }
}