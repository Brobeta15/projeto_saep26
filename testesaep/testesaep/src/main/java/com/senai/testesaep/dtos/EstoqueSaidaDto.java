package com.senai.testesaep.dtos;

public class EstoqueSaidaDto {

    private String produto;

    private int qtdSaida;

    private Long produtoId;

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQtdSaida() {
        return qtdSaida;
    }

    public void setQtdSaida(int qtdSaida) {
        this.qtdSaida = qtdSaida;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }
}
