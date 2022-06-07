package com.company;

public class Avatar {
    Integer id;
    String cor;


    public Avatar(Integer id, String cor) {
        this.id = id;
        this.cor = cor;
    }

    public Avatar() {
        this.id = 0;
        this.cor = "null";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }



    public Integer getId() {
        return id;
    }

    public String getCor() {
        return cor;
    }



}
