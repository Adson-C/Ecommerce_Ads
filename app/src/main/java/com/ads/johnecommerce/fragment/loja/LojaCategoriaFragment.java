package com.ads.johnecommerce.fragment.loja;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.databinding.DialogFormCategBinding;
import com.ads.johnecommerce.databinding.FragmentLojaCategoriaBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class LojaCategoriaFragment extends Fragment {

    private DialogFormCategBinding categBinding;
    private String caminhoImagem = null;
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

        categBinding = DialogFormCategBinding
                .inflate(LayoutInflater.from(getContext()));

        categBinding.btnFechar.setOnClickListener(view -> {
            dialog.dismiss();
        });

        categBinding.btnSalvar.setOnClickListener(view -> {
            dialog.dismiss();
        });
        categBinding.imgCategoria.setOnClickListener(view -> verificaPermissionGaleria());

        builder.setView(categBinding.getRoot());

        dialog = builder.create();
        dialog.show();
    }

    private void verificaPermissionGaleria(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleria();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getContext(), "Permisão Negada.", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedTitle("Permissões negadas.")
                .setDeniedMessage("Você negou as permissões para acessar a galeria do dispositivo, deseja permitir ?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        resultLauncher.launch(intent);
    }
    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    // Recuperar caminho da imagem
                    Uri imagemSelecionada = result.getData().getData();
                    caminhoImagem = imagemSelecionada.toString();

                    try {
                        Bitmap  bitmap;
                        if (Build.VERSION.SDK_INT < 28){
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imagemSelecionada);

                        }else {
                            ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), imagemSelecionada);
                            bitmap = ImageDecoder.decodeBitmap(source);
                        }
                        categBinding.imgCategoria.setImageBitmap(bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
    );
}