package com.senai.testesaep.dtos;

import com.senai.testesaep.Models.ProdutoModel;
import com.senai.testesaep.Models.UsuarioModel;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class EstoqueDto {

    private Long id;

    private int qtdSaida;

    private int qtdEntrada;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    private LocalTime hora;

    private String usuario;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQtdSaida() {
        return qtdSaida;
    }

    public void setQtdSaida(int qtdSaida) {
        this.qtdSaida = qtdSaida;
    }

    public int getQtdEntrada() {
        return qtdEntrada;
    }

    public void setQtdEntrada(int qtdEntrada) {
        this.qtdEntrada = qtdEntrada;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
