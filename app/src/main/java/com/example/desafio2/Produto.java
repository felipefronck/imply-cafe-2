package com.example.desafio2;

public class Produto {

    private int id;
    private Double precoProduto;
    private String nomeProduto, descProduto, imgProduto;

    public Produto(int id, String imgProduto, String nomeProduto , String descProduto, Double precoProduto) {
        this.id = id;
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
