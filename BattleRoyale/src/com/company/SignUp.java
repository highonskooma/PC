package com.company;
import java.util.*;

public class SignUp extends Menu {
    String email;

    public SignUp() {
        super();
        this.email="";
    }

    public void mostrarMenuSignUpU() {
        System.out.println("Nome");
        this.user.setNome( leString() );
        System.out.println("\nPassword");
        this.user.setPass( leString() );
        System.out.println("\nEmail");
        this.setEmail( leString() );
        //logins.put(this.nomeutilizador,this.passwd);
    }

    public String leString() {
        Scanner in = new Scanner(System.in);
        String result = new String();
    
        try {
          result = in.nextLine();
        } catch (InputMismatchException e) {
          System.out.println("Não foi detetado nenhum input compatível com o pedido.");
        }
    
        return result;
    }

    public void startSignUp() {
        this.mostrarMenuSignUpU();

        System.out.println(this.user.toString());
        System.out.print("Registado com sucesso!!\nA redireciona-lo para página inicial...\n\n\n");
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
  