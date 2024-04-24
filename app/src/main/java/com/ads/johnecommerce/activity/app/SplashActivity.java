package com.ads.johnecommerce.activity.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.activity.usuario.MainActivityUsuario;

public class  SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(getMainLooper()).postDelayed(() ->{
            finish();
            startActivity(new Intent(this, MainActivityUsuario.class));

        }, 3000);
    }

}