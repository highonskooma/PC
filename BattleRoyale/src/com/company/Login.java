package com.company;

public class Login extends Menu {

    public Login() {
        super();
    }

    public boolean userLogIn(String username){
        for (Utilizador user: this.logins){
            String name = user.getNome();
            if (name.equals(username))
                return true;
        }
        System.out.println("Username errado. Tente novamente.");
        return false;
    }

    public boolean VerificaPW(String username, String pw){
        for (Utilizador user: this.logins){
            String name=user.getNome();
            if (name.equals(username)){
                String pass= user.getPass();
                if (pass.equals(pw)){
                    System.out.println("Login Efetuado.");
                    return true;
                }
                else {
                    System.out.println("Password Errada. Tente Novamente.");
                    return false;
                }
            }
        }
        return false;
    }

    // Método que efetua o login de um cliente atraves do email
    public void mostrarMenuLogIn() {
        String name;
        String pw;
        System.out.println("Nome");
        name=leString();
        if(!userLogIn(name)) return;
        System.out.println("\nPassword");
        pw=leString();
        if(!VerificaPW(name,pw)) return;

        for(Utilizador user : this.logins) {
            if(user.getNome().equals(name)) {
                setUser(user);

            }
        }

    }

    public void startLogIn() {
        mostrarMenuLogIn();
    }
}