package com.ads.johnecommerce.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.databinding.ActivityCadastroBinding;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void configClicks() {

        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.btnCadstrase.setOnClickListener(v -> finish());

    }
}