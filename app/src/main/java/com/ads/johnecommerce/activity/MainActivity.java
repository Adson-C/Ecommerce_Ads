package com.ads.johnecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ads.johnecommerce.autenticacao.LoginActivity;
import com.ads.johnecommerce.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoginMain.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}