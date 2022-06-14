package com.company;

public class Login extends Menu {

    public Login() {
        super();
    }

    // Método que efetua o login de um cliente atraves do email
    public void mostrarMenuLogIn() {
        System.out.println("Nome");
        this.setName( leString() );
        System.out.println("\nPassword");
        this.setPass( leString() );
    }

    public void startLogIn() {
        mostrarMenuLogIn();
        System.out.println("user: "+this.username +"\npassword: "+this.password);
    }
}