package com.company;

public class Login extends Menu {

    public Login() {
        super();
    }

    // Método que efetua o login de um cliente atraves do email
    public void mostrarMenuLogIn() {
        String name;
        String pw;
        System.out.println(this.logins);
        System.out.println("Nome");
        name=leString();
        if(!userLogIn(name)) return;
        System.out.println("\nPassword");
        pw=leString();
        if(!VerificaPW(name,pw)) return;

        this.user.setNome( name );
        this.user.setPass( pw );
    }

    public void startLogIn() {
        mostrarMenuLogIn();
        //System.out.println("user: "+this.username +"\npassword: "+this.password);
    }
}