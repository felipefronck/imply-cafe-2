package com.example.desafio2;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import androidx.room.Entity;

@Entity(tableName = "Produto")
public class Produto {

    @ColumnInfo(name = "id_produto")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "preco_produto")
    private Double precoProduto;
    @ColumnInfo(name = "nome_produto")
    private String nomeProduto;
    @ColumnInfo(name = "desc_produto")
    private String descProduto;
    @ColumnInfo(name = "img_produto")
    private String imgProduto;;

    public Produto(String imgProduto, String nomeProduto , String descProduto, Double precoProduto) {
        this.id = 0;
        this.imgProduto = imgProduto;
        this.nomeProduto = nomeProduto;
        this.descProduto = descProduto;
        this.precoProduto = precoProduto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgProduto() {
        return imgProduto;
    }

    public void setImgProduto(String imgProduto) {
        this.imgProduto = imgProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }

    public Double getPrecoProduto() {
        return precoProduto;
    }

    public void setPrecoProduto(Double precoProduto) {
        this.precoProduto = precoProduto;
    }
}
