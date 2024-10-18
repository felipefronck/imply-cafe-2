package com.example.desafio2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoViewHolder> {

    private Context context;
    private ArrayList<Produto> itens;
    private ProdutoDatabse produtoDb;


    public ProdutoAdapter(Context context, ArrayList<Produto> itens, ProdutoDatabse produtoDb) {
        this.context = context;
        this.itens = itens;
        this.produtoDb = produtoDb;
    }

    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.produto_view, parent, false);
        ProdutoViewHolder viewHolder = new ProdutoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder produtoViewHolder, int position) {
        Produto produto = itens.get(position);
        produtoViewHolder.nomeProduto.setText(produto.getNomeProduto());
        produtoViewHolder.descProduto.setText(produto.getDescProduto());
        produtoViewHolder.precoProduto.setText(String.valueOf(produto.getPrecoProduto()));

        Glide.with(produtoViewHolder.itemView.getContext())
                .load(produto.getImgProduto())
                .into(produtoViewHolder.imgProduto);

        produtoViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Confirmação de exclusão:")
                        .setMessage("Deseja excluir esse produto?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int position = produtoViewHolder.getAdapterPosition();

                                Executor executor = Executors.newSingleThreadExecutor();
                                executor.execute(() -> {
                                    produtoDb.getProdutoDAO().deleteProduto(produto);

                                    ((Activity)context).runOnUiThread(() -> {
                                        itens.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, getItemCount());
                                    });
                                });
                            }
                        })
                        .setNegativeButton("Não", null)
                        .show();
                return true;
            }
        });

        produtoViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Produto produtoSelecionado = itens.get(produtoViewHolder.getAdapterPosition());

                Intent intent = new Intent(context, ProdutoAdd.class);
                intent.putExtra("id", produtoSelecionado.getId());
                intent.putExtra("nomeProduto", produtoSelecionado.getNomeProduto());
                intent.putExtra("descProduto", produtoSelecionado.getDescProduto());
                intent.putExtra("precoProduto", produtoSelecionado.getPrecoProduto());
                intent.putExtra("imgProduto", produtoSelecionado.getImgProduto());

                ((MainActivity) context).startActivityForResult(intent, 2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }
}
