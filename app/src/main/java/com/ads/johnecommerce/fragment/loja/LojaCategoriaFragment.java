package com.ads.johnecommerce.fragment.loja;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.databinding.DialogFormCategBinding;
import com.ads.johnecommerce.databinding.FragmentLojaCategoriaBinding;

public class LojaCategoriaFragment extends Fragment {

    private FragmentLojaCategoriaBinding binding;
    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLojaCategoriaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configClicks();
    }

    private void configClicks() {
        binding.btncategoria.setOnClickListener(v -> showDialog());
    }

    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(
                getContext(), R.style.CustomAlertDialog);

        DialogFormCategBinding categBinding = DialogFormCategBinding
                .inflate(LayoutInflater.from(getContext()));

        categBinding.btnFechar.setOnClickListener(view -> {
            dialog.dismiss();
        });

        categBinding.btnSalvar.setOnClickListener(view -> {
            dialog.dismiss();
        });

        builder.setView(categBinding.getRoot());

        dialog = builder.create();
        dialog.show();
    }
}