package com.cursoandroid.wms;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Produto {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nome;
    public String descricao;
    public double preco;
    public int quantidade;

    public Produto(String nome, String descricao, double preco, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }
}

