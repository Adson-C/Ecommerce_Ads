package com.ads.johnecommerce.autenticacao;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    // metodo para passar dados para outras actvity
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
               if (result.getResultCode() == RESULT_OK){
                   String email = result.getData().getStringExtra("email");
                   binding.edtEmail.setText(email);
               }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    private void configClicks() {
        binding.btnRecuperarSenha.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));

        binding.btnCadstrase.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            // metodo para passar dados para outras actvity
            resultLauncher.launch(intent);
        });
    }
}