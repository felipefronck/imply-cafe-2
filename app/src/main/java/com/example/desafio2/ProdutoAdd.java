package com.example.desafio2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ProdutoAdd extends AppCompatActivity {

    ImageView imgPreviewAdd;
    EditText nomeProdutoAdd, descProdutoAdd, precoProdutoAdd;
    Button btnSalvarProduto;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produto_add);

        imgPreviewAdd = findViewById(R.id.imgPreviewAdd);
        nomeProdutoAdd = findViewById(R.id.nomeProdutoAdd);
        descProdutoAdd = findViewById(R.id.descProdutoAdd);
        precoProdutoAdd = findViewById(R.id.precoProdutoAdd);
        btnSalvarProduto = findViewById(R.id.btnSalvarProduto);

        Intent intent = getIntent();

        if(intent.hasExtra("nomeProduto")){
            String nomeProduto = intent.getStringExtra("nomeProduto");
            String descProduto = intent.getStringExtra("descProduto");
            double precoProduto = intent.getDoubleExtra("precoProduto", 0.0);
            imageUrl = intent.getStringExtra("imgProduto");

            nomeProdutoAdd.setText(nomeProduto);
            descProdutoAdd.setText(descProduto);
            precoProdutoAdd.setText(String.valueOf(precoProduto));

            if(imageUrl != null && !imageUrl.isEmpty()){
                Glide.with(this).load(imageUrl).into(imgPreviewAdd);
            }
        }

        imgPreviewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(ProdutoAdd.this);
                input.setHint("Digite a URL da imagem");

                new AlertDialog.Builder(ProdutoAdd.this)
                        .setTitle("Carregar Imagem")
                        .setMessage("Insira a URL da imagem")
                        .setView(input)
                        .setPositiveButton("Carregar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url = input.getText().toString();
                                if (!url.isEmpty()) {
                                    imageUrl = url;
                                    Glide.with(ProdutoAdd.this)
                                            .load(url)
                                            .into(imgPreviewAdd);
                                } else {
                                    url = "https://compras.wiki.ufsc.br/images/5/56/Erro.png";
                                    imageUrl = url;
                                    Glide.with(ProdutoAdd.this)
                                            .load(url)
                                            .into(imgPreviewAdd);
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });

        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeProduto = nomeProdutoAdd.getText().toString();
                String descProduto = descProdutoAdd.getText().toString();
                String precoProdutoString = precoProdutoAdd.getText().toString();

                if (!nomeProduto.isEmpty() && !descProduto.isEmpty() && !precoProdutoString.isEmpty()) {
                    double precoProduto = Double.parseDouble(precoProdutoString);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("nomeProduto", nomeProduto);
                    resultIntent.putExtra("descProduto", descProduto);
                    resultIntent.putExtra("precoProduto", precoProduto);
                    resultIntent.putExtra("imageUrl", imageUrl);

                    int id = getIntent().getIntExtra("id", -1);
                    resultIntent.putExtra("id", id);

                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });

    }
}