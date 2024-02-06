package com.ads.johnecommerce.autenticacao;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.databinding.ActivityCadastroBinding;
import com.ads.johnecommerce.helper.FirebaseHelper;
import com.ads.johnecommerce.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configClicks();
    }

    public void validaDados(View view) {

        String nome = binding.edtNome.getText().toString().trim();
        String email = binding.edtEmal.getText().toString().trim();
        String senha = binding.edtSena.getText().toString().trim();
        String confirmasenha = binding.edtSenConfirma.getText().toString().trim();

        if (!nome.isEmpty()){
            if (!email.isEmpty()){
                if (!senha.isEmpty()){
                    if (!confirmasenha.isEmpty()){
                        if (senha.equals(confirmasenha)){

                            binding.progressBarCriar.setVisibility(View.VISIBLE);

                            Usuario usuario = new Usuario();
                                usuario.setNome(nome);
                                usuario.setEmail(email);
                                usuario.setSenha(senha);

                                criarConta(usuario);

                        }else {
                            binding.edtSenConfirma.requestFocus();
                            binding.edtSenConfirma.setError("Senhas não conferi.");
                        }

                    }else {
                        binding.edtSenConfirma.requestFocus();
                        binding.edtSenConfirma.setError("Confirme sua senha.");
                    }

                }else {
                    binding.edtSena.requestFocus();
                    binding.edtSena.setError("Informe sua senha.");
                }

            }else {
                binding.edtEmal.requestFocus();
                binding.edtEmal.setError("Informe seu E-mail.");
            }
        }else {
            binding.edtNome.requestFocus();
            binding.edtNome.setError("Informe seu nome.");
        }
    }

    private void criarConta(Usuario usuario){
        FirebaseHelper.getAuth().createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                // chamando metodo para slavar o id do usuario
                String id = task.getResult().getUser().getUid();

                usuario.setId(id);
                // metoto invocado da classe Usuario
                usuario.salvar();

            }else {
                // chamando a class FirebaseHelper --> validaErros no TOAST
                Toast.makeText(this, FirebaseHelper.validaErros(task.getException().getMessage()), Toast.LENGTH_SHORT).show();

                }

            binding.progressBarCriar.setVisibility(View.GONE);
        });

    }


    private void configClicks() {

        binding.include.ibVoltar.setOnClickListener(v -> finish());
        binding.btnCadstrase.setOnClickListener(v -> finish());

    }
}