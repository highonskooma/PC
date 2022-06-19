import com.company.GreetClient;
import com.company.Utilizador;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import processing.core.PApplet;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    Tests calculator;
    GreetClient client = new GreetClient();

    @Test
    public void twoClients() {
        new Thread(() -> {
            GreetClient client = new GreetClient();
            try {
                client.startConnection("127.0.0.1",8091);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PApplet.main("com.company.Main", new String[]{"blackgaze","2"});
        }).start();
        new Thread(() -> {
            GreetClient client = new GreetClient();
            try {
                client.startConnection("127.0.0.1",8091);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PApplet.main("com.company.Main", new String[]{"highonskooma","2"});
        }).start();

    }

    @Test
    public void manyClients_whenServerBroadcast_theCorrect() throws IOException, InterruptedException {
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
        for (int i=0; i<N; i++) ts[i].join();
    }



}
