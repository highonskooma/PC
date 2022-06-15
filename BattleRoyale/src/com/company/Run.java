package com.company;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Run {
    private Menu menuInicial;
    private Login menuLogIn;
    private SignUp menuSignUp;

    public Run() {
        this.menuInicial = new Menu();
        this.menuLogIn = new Login();
        this.menuSignUp = new SignUp();
    }

    // Método que lê a opção pretendida do menu inicial e redireciona para essa função (login/signup/leaderbords)
    public void startInicial() {
        try (FileInputStream fis = new FileInputStream("/home/blackgaze/Documents/PC/BattleRoyale/src/obj");
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // read object from file
            ArrayList<Utilizador> locals = (ArrayList<Utilizador>) ois.readObject();
            this.menuInicial.setLogins(locals);
            // print object
            System.out.println("Utilizadores atuais: "+locals);

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        this.menuInicial.setOpcao(0);

        while (this.menuInicial.getOpcao() != 4) {
            System.out.print("\tTraz Aqui\n\n");
            this.menuInicial.mostrarMenuInicial();

            switch (this.menuInicial.getOpcao()) {
                case 1: // Log in
                    this.menuLogIn.startLogIn();
                    break;
                case 2: // Sign up
                    this.menuSignUp.startSignUp();
                    //for(int i=0;i<menuInicial.logins.size();i++) {
                    this.menuInicial.addUser(menuSignUp.getUser().clone());
                    System.out.println("MenuSignUp: "+this.menuSignUp.getUser().toString());
                    System.out.println("MenuInicial: "+this.menuInicial.logins.toString());
                    break;
                case 3: // Leaderboards
                    break;
                case 4: // Sair
                    WriteObjectToFile(this.menuInicial.getLogins());
                    break;
                default:
                    System.out.print("A opcão que escolheu é inválida.\n\n\n");
                    continue;
            }
        }
    }

    final String filepath="/home/blackgaze/Documents/PC/BattleRoyale/src/obj";
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

    public static void main(String[] args) throws IOException {
        //new Run().startInicial();

    }

}
