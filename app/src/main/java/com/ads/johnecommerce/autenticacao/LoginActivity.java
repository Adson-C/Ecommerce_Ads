package com.ads.johnecommerce.autenticacao;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.activity.loja.MainActivityEmpresa;
import com.ads.johnecommerce.activity.usuario.MainActivityUsuario;
import com.ads.johnecommerce.databinding.ActivityLoginBinding;
import com.ads.johnecommerce.helper.FirebaseHelper;
import com.ads.johnecommerce.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

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

    public void validaDadosLogin(View view) {

        String email = binding.edtEmail.getText().toString().trim();
        String senha = binding.edtSenha.getText().toString().trim();

            if (!email.isEmpty()){
                if (!senha.isEmpty()){
                    binding.progressBarLogin.setVisibility(View.VISIBLE);
                    // metodo para logar
                    login(email, senha);

                }else {
                    binding.edtSenha.requestFocus();
                    binding.edtSenha.setError("Informe sua senha.");
                }

            }else {
                binding.edtEmail.requestFocus();
                binding.edtEmail.setError("Informe seu E-mail.");
            }
    }

    // metodo para fazer login no app
    private void login(String email, String senha){
        FirebaseHelper.getAuth().signInWithEmailAndPassword(
                email, senha
        ).addOnCompleteListener(task -> {
           if (task.isSuccessful()){
               recuperaUsuario(task.getResult().getUser().getUid());

               finish();
           }else {
               Toast.makeText(this, FirebaseHelper.validaErros(task.getException().getMessage()), Toast.LENGTH_SHORT).show();
           }
            binding.progressBarLogin.setVisibility(View.GONE);
        });
    }

    private void recuperaUsuario(String id) {
        DatabaseReference usuariRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(id);
        usuariRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) { // Se for Usuario
                    startActivity(new Intent(getBaseContext(), MainActivityUsuario.class));
                }else { //  se Loja
                    startActivity(new Intent(getBaseContext(), MainActivityEmpresa.class));
                }
                    finish();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void configClicks() {
        binding.include.ibVoltar.setOnClickListener(view -> finish());

        binding.btnRecuperarSenha.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));

        binding.btnCadstrase.setOnClickListener(v -> {
            Intent intent = new Intent(this, CadastroActivity.class);
            // metodo para passar dados para outras actvity
            resultLauncher.launch(intent);
        });
    }
}