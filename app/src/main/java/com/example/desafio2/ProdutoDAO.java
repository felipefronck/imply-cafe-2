package com.example.desafio2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface ProdutoDAO {

    @Insert
    public void addProduto(Produto produto);

    @Update
    public void updateProduto(Produto produto);

    @Delete
    public void deleteProduto(Produto produto);

    @Query("SELECT * FROM produto")
    public List<Produto> getAllProdutos();

    @Query("SELECT * FROM produto WHERE id_produto LIKE :id_produto")
    public Produto getPerson(int id_produto);

}
