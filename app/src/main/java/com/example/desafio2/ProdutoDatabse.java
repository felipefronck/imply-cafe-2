package com.example.desafio2;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.RoomDatabase;

@Database(entities = {Produto.class}, version = 3, exportSchema = false)
public abstract class ProdutoDatabse extends RoomDatabase {

    public abstract ProdutoDAO getProdutoDAO();
}
