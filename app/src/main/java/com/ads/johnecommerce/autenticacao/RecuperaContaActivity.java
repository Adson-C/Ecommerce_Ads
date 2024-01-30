package com.ads.johnecommerce.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ads.johnecommerce.databinding.ActivityRecuperaContaBinding;

public class RecuperaContaActivity extends AppCompatActivity {

    private ActivityRecuperaContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }
    private void configClicks(){
        binding.btnBack.ibVoltar.setOnClickListener(v -> finish());
    }
}