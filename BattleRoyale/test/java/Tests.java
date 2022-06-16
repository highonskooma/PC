import com.company.GreetClient;
import com.company.Utilizador;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    Tests calculator;
    GreetClient client = new GreetClient();

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 8091);

        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");
        String resp4 = client.sendMessage("good bye");

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("good bye", resp4);

        client.stopConnection();
    }

    @Test
    public void givenGreetingClient_whenServerRespondsWhenStarted_thenCorrect() throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 8091);
        String response = client.sendMessage("hello server");
        System.out.println(response);
        assertEquals("hello server", response);
    }

    @Test
    public void givenUtilizador_whenServerResponds_thenCorrect() throws IOException, ClassNotFoundException {
        GreetClient client = new GreetClient();
        Utilizador user = new Utilizador();
        client.startConnection("127.0.0.1",8091);
        String resp = client.sendMessage(user.toString());
        System.out.println(resp);
    }

    @Test
    public void manyClients_whenServerBoradcast_theCorrect() throws IOException, InterruptedException {
        int N=3;
        Thread[] ts = new Thread[N];
        for (int i=0; i<N; i++) {
            ts[i] = new Thread(() -> {
                try {
                    GreetClient c = new GreetClient();
                    c.startConnection("127.0.0.1",8091);
                    c.listener();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        for (int i=0; i<N; i++)	ts[i].start();
        for (int i=0; i<N; i++) ts[i].join();

    }

}
