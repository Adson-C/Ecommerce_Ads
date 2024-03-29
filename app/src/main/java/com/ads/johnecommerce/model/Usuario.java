package com.ads.johnecommerce.model;

import com.ads.johnecommerce.helper.FirebaseHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;



public class Usuario implements Serializable {

    private String id;
    private String nome;
    private String email;
    private String senha;
    private String confimaSenha;

    public Usuario() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfimaSenha() {
        return confimaSenha;
    }

    public void setConfimaSenha(String confimaSenha) {
        this.confimaSenha = confimaSenha;
    }

    // metodo para salvar usuario no FirebaseHelper
    public void salvar() {
        DatabaseReference usuariRef = FirebaseHelper.getDatabaseReference()
                .child("usuarios")
                .child(this.getId());
        usuariRef.setValue(this);

    }

}
