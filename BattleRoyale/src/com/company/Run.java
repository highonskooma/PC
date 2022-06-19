package com.company;
import processing.core.PApplet;

import java.io.*;
import java.util.ArrayList;

public class Run extends PApplet{
    private Menu menuInicial;
    private Login menuLogIn;
    private SignUp menuSignUp;
    final String filepath="/home/blackgaze/Documents/PC/BattleRoyale/src/obj";
    boolean winner = false;

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

            // print object
            System.out.println("Contas: "+locals);

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        this.menuInicial.setOpcao(0);
        this.menuInicial.setNome("Anonimo");
        System.out.println("Utilizador Atual: Anónimo");

        while (this.menuInicial.getOpcao() != 5) {
            System.out.print("\tAgar.IO\n\n");
            this.menuInicial.mostrarMenuInicial();
            if (winner) {
                for ( Utilizador u : this.menuInicial.getLogins()) {
                    if (u.getNome().equals(this.menuInicial.getUser().getNome())) {
                        u.incrementVictory();
                        System.out.println("vitoria++");
                    }
                }
                winner = false;
            }
            switch (this.menuInicial.getOpcao()) {
                case 1: // partida
                    try {
                        System.out.println("Por favor aguarde.");
                        winner = true;
                        startConnection();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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

    public void startConnection() throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1",8091);
        String[] server_reply = client.listener().split(" ");
        System.out.println(server_reply[0]);
        if (server_reply[0].equals("start")) {
            PApplet.main("com.company.Main", new String[]{this.menuInicial.getUser().getNome(),server_reply[1]});
        }
    }

    public static void main(String[] args) {
        new Run().startInicial();
        /*
        int N=2;
        Thread[] ts = new Thread[N];
        for (int i=0; i<N; i++) {
            int finalI = i;
            ts[i] = new Thread(() -> {
                try {
                    GreetClient c = new GreetClient();
                    c.startConnection("127.0.0.1",8091);
                    String user = "user";
                    user.concat(String.valueOf(finalI));
                    PApplet.main("com.company.Main", new String[]{user,"2"});
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i=0; i<N; i++)	ts[i].start();
        //for (int i=0; i<N; i++) ts[i].join();

         */
    }

}
