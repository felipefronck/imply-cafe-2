package com.example.desafio2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ProdutoAdapter adapter;
    private ArrayList<Produto> itens;

    private ProdutoDatabse produtoDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        produtoDb = Room.databaseBuilder(getApplicationContext(), ProdutoDatabse.class, "ProdutoDb")
                .fallbackToDestructiveMigration()
                .build();

        recycler = findViewById(R.id.recycler);
        itens = new ArrayList<>();
        adapter = new ProdutoAdapter(MainActivity.this, itens, produtoDb);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

        carregaDatabase();

        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarProdutos();

            }
        });
    }

    private void adicionarProdutos() {
        Intent intent = new Intent(MainActivity.this, ProdutoAdd.class);
        startActivityForResult(intent, 1);
    }

    private void carregaDatabase(){
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Produto> produtos = produtoDb.getProdutoDAO().getAllProdutos();

            runOnUiThread(() -> {
                itens.clear();
                itens.addAll(produtos);
                adapter.notifyDataSetChanged();
            });
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            String name = data.getStringExtra("nomeProduto");
            String description = data.getStringExtra("descProduto");
            Double price = data.getDoubleExtra("precoProduto", 0.0);
            String imageUrl = data.getStringExtra("imageUrl");
            int id = data.getIntExtra("id", -1);

            if(requestCode == 1){
                addProductToRecyclerView(imageUrl, name, description, price);
            } else if (requestCode == 2 && id != -1){
                updateProductInRecyclerView(id, imageUrl, name, description, price);
            }
        }
    }

    private void addProductToRecyclerView(String imageUrl, String productName, String productDescription, Double productPrice) {
        Produto newProduto = new Produto(imageUrl, productName, productDescription, productPrice);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            produtoDb.getProdutoDAO().addProduto(newProduto);
            runOnUiThread(() -> {
                itens.add(newProduto);
                adapter.notifyItemInserted(itens.size() - 1);
            });
        });
    }

    private void updateProductInRecyclerView(int id, String imageUrl, String productName, String productDescription, Double productPrice) {
        for (int i = 0; i < itens.size(); i++) {
            final int position = i;
            Produto produto = itens.get(i);
            if (produto.getId() == id) {
                produto.setNomeProduto(productName);
                produto.setDescProduto(productDescription);
                produto.setPrecoProduto(productPrice);
                produto.setImgProduto(imageUrl);
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    produtoDb.getProdutoDAO().updateProduto(produto);
                    runOnUiThread(() -> {
                        adapter.notifyItemChanged(position);
                    });
                });

                break;
            }
        }
    }
}
