package com.example.desafio2;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProdutoViewHolder extends RecyclerView.ViewHolder {

    ImageView imgProduto;
    TextView nomeProduto, descProduto, precoProduto;

    public ProdutoViewHolder(@NonNull View itemView) {
        super(itemView);
        imgProduto = itemView.findViewById(R.id.imgProduto);
        nomeProduto = itemView.findViewById(R.id.nomeProduto);
        descProduto = itemView.findViewById(R.id.descProduto);
        precoProduto = itemView.findViewById(R.id.precoProduto);

    }
}
