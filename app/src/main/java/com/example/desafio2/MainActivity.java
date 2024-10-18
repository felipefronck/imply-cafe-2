package com.example.desafio2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ProdutoAdapter adapter;
    private ArrayList<Produto> itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        itens = new ArrayList<>();
        adapter = new ProdutoAdapter(MainActivity.this, itens);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);

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
        int id = itens.size() + 1;
        Produto newProduct = new Produto(id, imageUrl, productName, productDescription, productPrice);
        itens.add(newProduct);
        adapter.notifyItemInserted(itens.size() - 1);
    }

    private void updateProductInRecyclerView(int id, String imageUrl, String productName, String productDescription, Double productPrice) {
        for (int i = 0; i < itens.size(); i++) {
            Produto produto = itens.get(i);
            if (produto.getId() == id) {
                produto.setNomeProduto(productName);
                produto.setDescProduto(productDescription);
                produto.setPrecoProduto(productPrice);
                produto.setImgProduto(imageUrl);

                adapter.notifyItemChanged(i);
                break;
            }
        }
    }
}
