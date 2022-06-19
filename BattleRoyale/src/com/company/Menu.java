package com.company;
import java.util.*;

public class Menu {
    private int opcao;
    Utilizador user;
    ArrayList<Utilizador> logins;

    public Menu() {
        this.opcao=0;
        this.user = new Utilizador();
        this.logins = new ArrayList<>();
    }

    public int getOpcao() {
        return this.opcao;
    }

    public void setOpcao(int newOpcao) {
        this.opcao = newOpcao;
    }
    
    public int leOpcao() {
        Scanner in = new Scanner(System.in);
        int opcao = -1;
        try {
            opcao = in.nextInt();
        } catch (InputMismatchException e) {
            return Integer.MAX_VALUE;
        }
        return opcao;
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

    public void mostrarMenuInicial() {
        System.out.println("1: Jogar");
        System.out.println("2: Log in");
        System.out.println("3: Registo");
        System.out.println("4: Leaderbord");
        System.out.println("5: Sair");

        //System.out.println("Utilizadores atuais: "+this.logins);
        int opcaoAUX = leOpcao();
        if (opcaoAUX == Integer.MAX_VALUE) {
          System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
          System.out.print("\tAgar.IO\n\n");
          mostrarMenuInicial();
        }
        else
          setOpcao(opcaoAUX);
      }

    public void setNome(String name) {
        this.user.setNome(name);
    }
    


    public ArrayList<Utilizador> getLogins() {
        return this.logins;
    }

    public Utilizador getUser() {
        return this.user;
    }

    public void setUser(Utilizador u) {
        this.user = u;
    }

    public void addUser(Utilizador u) {
        this.logins.add(u);
    }

    public void setLogins(ArrayList<Utilizador> array) {
        this.logins = array;
    }

}