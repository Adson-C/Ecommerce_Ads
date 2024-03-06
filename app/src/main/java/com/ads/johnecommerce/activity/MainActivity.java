package com.ads.johnecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ads.johnecommerce.autenticacao.LoginActivity;
import com.ads.johnecommerce.databinding.ActivityMainBinding;
import com.ads.johnecommerce.helper.FirebaseHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoginMain.setOnClickListener(v -> {
            if (FirebaseHelper.getAutenticao()){
                FirebaseHelper.getAuth().signOut();
                Toast.makeText(this, "Usuario já autenticado", Toast.LENGTH_SHORT).show();
            }else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });
    }
}