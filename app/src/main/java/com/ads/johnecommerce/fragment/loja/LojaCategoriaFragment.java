package com.ads.johnecommerce.fragment.loja;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.databinding.DialogFormCategBinding;
import com.ads.johnecommerce.databinding.FragmentLojaCategoriaBinding;
import com.ads.johnecommerce.helper.FirebaseHelper;
import com.ads.johnecommerce.model.Categoria;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class LojaCategoriaFragment extends Fragment {


    private DialogFormCategBinding categBinding;
    private String caminhoImagem = null;
    private FragmentLojaCategoriaBinding binding;
    private AlertDialog dialog;
    private Categoria categoria;

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

            String nomeCategoria = categBinding.edtCategoria.getText().toString().trim();

            if (!nomeCategoria.isEmpty()){
                if (caminhoImagem != null){

                    ocultarTeclado();

                    categBinding.progressDialog.setVisibility(View.VISIBLE);

                    if (categoria == null) categoria = new Categoria();
                    categoria.setNome(nomeCategoria);
                    categoria.setTodos(categBinding.cbCategoria.isChecked());

                    salvarImagensFirebase();

                }else {
                    ocultarTeclado();
                    Toast.makeText(getContext(), "Escolha uma imagem para categoria.", Toast.LENGTH_SHORT).show();
                }
            }else {
                categBinding.edtCategoria.setError("Nome Obrigatório.");
            }
        });
        categBinding.imgCategoria.setOnClickListener(view -> verificaPermissionGaleria());

        builder.setView(categBinding.getRoot());

        dialog = builder.create();
        dialog.show();
    }

    private void salvarImagensFirebase() {
        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("imagens")
                .child("categorias")
                .child(categoria.getId() + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(caminhoImagem));
        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {

            String urlImagem = task.getResult().toString();
            categoria.setUrlImagem(urlImagem);
            categoria.Salvar();

            categoria = null;
            dialog.dismiss();

        })).addOnFailureListener(e -> {
            dialog.dismiss();
            Toast.makeText(getContext(), "Erro ao fazer upload da imagem.", Toast.LENGTH_SHORT).show();
        });

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

    // Metodo para Ocultar o Teclado
    private void ocultarTeclado(){
        InputMethodManager inputMethodManager =
                (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(categBinding.edtCategoria.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}