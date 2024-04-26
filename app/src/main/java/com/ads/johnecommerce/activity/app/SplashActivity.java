package com.ads.johnecommerce.activity.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.activity.loja.MainActivityEmpresa;
import com.ads.johnecommerce.activity.usuario.MainActivityUsuario;
import com.ads.johnecommerce.helper.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class  SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        FirebaseHelper.getAuth().signOut();

        new Handler(getMainLooper()).postDelayed(this::verificarAcesso, 3000);
    }

    private void verificarAcesso(){
        if (FirebaseHelper.getAutenticao()){
                recuperaAcesso();
        }else {
            finish();
            startActivity(new Intent(this, MainActivityUsuario.class));

        }
    }

    private void recuperaAcesso() {
        DatabaseReference usuariRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(FirebaseHelper.getIdFirebase());
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

}