package com.company;

import jdk.jshell.execution.Util;

import java.io.Serializable;
import java.util.concurrent.Semaphore;

public class Utilizador implements Serializable {
    Integer id;
    String nome;
    private String password;
    Integer n_vitorias;
    Avatar circulo;

    public Utilizador() {
        this.id = 0;
        this.nome = "";
        this.password = "";
        this. n_vitorias = 0;
        this.circulo = new Avatar();
    }

    public Utilizador(Integer id, String nome, Integer n_vitorias, Avatar circulo) {
        this.id = id;
        this.nome = nome;
        this.n_vitorias = n_vitorias;
        this.circulo = circulo;
    }

    //construtor por objeto
    public Utilizador(Utilizador u){
        this.nome = u.getNome();
        this.password = u.getPass();
        this.circulo = u.getCirculo();
        this.n_vitorias = u.getN_vitorias();
        this.id = u.getId();
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

    public void setPass(String p) {
        this.password = p;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", password='" + password + '\'' +
                ", n_vitorias=" + n_vitorias +
                ", circulo=" + circulo +
                "}\n";
    }

    //Modified clone() method in Employee class
    public Utilizador clone() {
        return new Utilizador(this);
    }

    public String getPass() {
        return this.password;
    }
}
