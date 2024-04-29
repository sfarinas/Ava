package com.example.ava2;

public class Objeto {
    private long id;
    private String nome;
    private int quantidade;
    private double preco;
    private boolean disponivel;

    // Construtor padrão
    public Objeto() {
    }

    // Construtor com parâmetros
    public Objeto(long id, String nome, int quantidade, double preco, boolean disponivel) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    // Método toString para facilitar a exibição em ListView
    @Override
    public String toString() {
        return "Nome: " + nome + ", Quantidade: " + quantidade + ", Preço: " + preco + ", Disponível: " + disponivel;
    }
}
