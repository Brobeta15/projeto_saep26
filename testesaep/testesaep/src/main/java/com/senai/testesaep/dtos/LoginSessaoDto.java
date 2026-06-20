package com.senai.testesaep.dtos;

public class LoginSessaoDto {

    private Long id;

    private String nome;

    private String cargo;

    public LoginSessaoDto() {
        this.id = 0L;
        this.nome = "";
        this.cargo = "OPERADOR";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
