package com.company;

import jdk.jshell.execution.Util;

public class Utilizador {
    Integer id;
    String nome;
    Integer n_vitorias;
    Avatar circulo;


    public Utilizador(Integer id, String nome, Integer n_vitorias, Avatar circulo) {
        this.id = id;
        this.nome = nome;
        this.n_vitorias = n_vitorias;
        this.circulo = circulo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setN_vitorias(Integer n_vitorias) {
        this.n_vitorias = n_vitorias;
    }

    public void setCirculo(Avatar circulo) {
        this.circulo = circulo;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getN_vitorias() {
        return n_vitorias;
    }

    public Avatar getCirculo() {
        return circulo;
    }
}
