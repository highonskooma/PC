package com.company;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class Tests {

    Tests calculator;

    @BeforeEach
    void setUp() {
        calculator = new Tests();
    }

    @Before
    public void setup() throws IOException {
        GreetClient client = new GreetClient();
        client.startConnection("127.0.0.1", 8091);
    }

    @Test
    public void givenClient_whenServerEchosMessage_thenCorrect() {
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        String resp3 = client.sendMessage("!");
        String resp4 = client.sendMessage(".");

        assertEquals("hello", resp1);
        assertEquals("world", resp2);
        assertEquals("!", resp3);
        assertEquals("good bye", resp4);
    }

    @After
    public void tearDown() {
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


}
