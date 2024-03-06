package com.ads.johnecommerce.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ads.johnecommerce.databinding.ActivityRecuperaContaBinding;
import com.ads.johnecommerce.helper.FirebaseHelper;

public class RecuperaContaActivity extends AppCompatActivity {

    private ActivityRecuperaContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    public void validarDados(View view){
        String email = binding.edtEmaill.getText().toString().trim();

        if (!email.isEmpty()){
            binding.progresRecu.setVisibility(View.VISIBLE);

            recuperarConta(email);

        }else {
            binding.edtEmaill.requestFocus();
            binding.edtEmaill.setError("Informe seu email.");
        }

    }

    private void recuperarConta(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(
                email
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "Acabamos de enviar um link para o e-mail informado", Toast.LENGTH_SHORT).show();
            }else {

                Toast.makeText(this, FirebaseHelper.validaErros(task.getException().getMessage()), Toast.LENGTH_SHORT).show();
            }
            binding.progresRecu.setVisibility(View.GONE);
        });
    }

    private void configClicks(){
        binding.include.ibVoltar.setOnClickListener(v -> finish());
    }
}