package com.ads.johnecommerce.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void configClicks(){
        binding.btnRecuperarSenha.setOnClickListener(v -> {
            startActivity(new Intent(this, RecuperaContaActivity.class));
        });
    }
}