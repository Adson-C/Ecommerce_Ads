package com.ads.johnecommerce.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.johnecommerce.R;
import com.ads.johnecommerce.model.Categoria;

import java.util.List;

public class CategoriaAdapter extends RecyclerView.Adapter<CategoriaAdapter.MyViewHoder> {

    private final List<Categoria> categoriaList;

    private Onclick onclick;

    public CategoriaAdapter(List<Categoria> categoriaList, Onclick onclick) {
        this.categoriaList = categoriaList;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public MyViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categoria_horizontal, parent, false);
        return new MyViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoder holder, int position) {
        Categoria categoria = categoriaList.get(position);

        holder.itemView.setOnClickListener(v -> onclick.onClickListerner(categoria));

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface Onclick{
        public void onClickListerner(Categoria categoria);
    }

    public static class MyViewHoder extends RecyclerView.ViewHolder{

        public MyViewHoder(@NonNull View item) {
            super(item);

            ImageView imgCategoria = item.findViewById(R.id.imgCategoria);
            ImageView nomeCategoria = item.findViewById(R.id.nomeCategoria);
        }
    }

}
