package com.company;
import processing.core.PApplet;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Run extends PApplet{
    private Menu menuInicial;
    private Login menuLogIn;
    private SignUp menuSignUp;
    final String filepath="/home/blackgaze/Documents/PC/BattleRoyale/src/obj";

    public Run() {
        this.menuInicial = new Menu();
        this.menuLogIn = new Login();
        this.menuSignUp = new SignUp();
    }

    // Método que lê a opção pretendida do menu inicial e redireciona para essa função (login/signup/leaderbords)
    public void startInicial() {
        try (FileInputStream fis = new FileInputStream(filepath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // read object from file
            ArrayList<Utilizador> locals = (ArrayList<Utilizador>) ois.readObject();
            this.menuInicial.setLogins(locals);
            this.menuLogIn.setLogins(locals);
            //this.menuSignUp.setLogins(locals);
            // print object
            System.out.println("Contas: "+locals);

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        this.menuInicial.setOpcao(0);
        System.out.println("Utilizador Atual: Anónimo");

        while (this.menuInicial.getOpcao() != 5) {
            System.out.print("\tAgar.IO\n\n");
            this.menuInicial.mostrarMenuInicial();

            switch (this.menuInicial.getOpcao()) {
                case 1: // partida
                    Thread[] ts = new Thread[2];
                    ts[0] = new Thread(() -> {
                        PApplet.main("com.company.Main", new String[]{"highonskooma"});
                    });
                    ts[1] = new Thread(() -> {
                        PApplet.main("com.company.Main", new String[]{"blackgaze"});
                    });
                    ts[0].start();
                    ts[1].start();
                    break;
                case 2: // Log in
                    this.menuLogIn.startLogIn();
                    this.menuInicial.setUser(menuLogIn.getUser().clone());
                    System.out.println("Utilizador Atual: "+menuInicial.getUser().toString());
                    break;
                case 3: // Sign up
                    this.menuSignUp.startSignUp();
                    this.menuInicial.addUser(menuSignUp.getUser().clone());
                    this.menuInicial.setUser(menuSignUp.getUser().clone());
                    System.out.println("Utilizador Atual: "+menuInicial.getUser().toString());
                    break;
                case 4: // Leaderboards
                    break;
                case 5: // Sair
                    WriteObjectToFile(this.menuInicial.getLogins());
                    break;
                default:
                    System.out.print("A opcão que escolheu é inválida.\n\n\n");
                    continue;
            }
        }
    }


    public void WriteObjectToFile(ArrayList<Utilizador> serObj) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Run().startInicial();
    }

}
