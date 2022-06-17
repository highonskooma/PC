package com.company;

import java.io.*;
import java.net.Socket;

public class GreetClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {
        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public Utilizador sendMessage(Utilizador usr) throws IOException, ClassNotFoundException {
        ObjectOutputStream outStream = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inStream = new ObjectInputStream(clientSocket.getInputStream());

        outStream.writeObject(usr);

        Utilizador resp = (Utilizador) inStream.readObject();


        outStream.close();
        return resp;
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    public void listener() throws IOException {
        String line=null;
        while((line = in.readLine()) != null) {
            System.out.println(line);
        }

    }

    public BufferedReader getIn() {
        return this.in;
    }

}