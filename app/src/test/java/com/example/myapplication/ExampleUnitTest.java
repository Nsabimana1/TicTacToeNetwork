package com.example.myapplication;

import com.example.myapplication.peertopeernetworking.Communication;
import com.example.myapplication.peertopeernetworking.Server;
import com.example.myapplication.peertopeernetworking.Utilities;

import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    public static  String connectingMessage = "letUSConnect";
    public static  String rejectingConnectionMessage = "Don'tWantToConnect";
    public static  String acceptingConnectionMessage = "letUSConnect";
    private String localIpAddress = Utilities.getLocalIpAddress();

    public ExampleUnitTest() throws SocketException {
    }

    @Test
    public void test_send_receive() throws Exception {
        final String testMsg = "This is a test.\nThis is only a test.\n";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Server.get().listenOnce().start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Socket test = new Socket(localIpAddress, Server.APP_PORT);
        Communication.sendOver(test, testMsg);
        String result = Communication.receive(test);
        System.out.print("Ready to recieve");
        System.out.print(result);
        assertEquals(testMsg, result);
    }


    public boolean checkEqual(String messageToSend, String messageToReceive) throws Exception {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Server.get().listenOnce().start();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();
        Socket connectTestSocket = new Socket(localIpAddress, Server.APP_PORT);
        Communication.sendOver( connectTestSocket, messageToSend);
        return messageToReceive.equals(Communication.receive(connectTestSocket).trim());
    }

    @Test
    public void testIsConnected() throws Exception {
        assertTrue(checkEqual(connectingMessage, acceptingConnectionMessage));
    }

    @Test
    public void testIsNotConnected() throws Exception {
        assertFalse(checkEqual(connectingMessage, rejectingConnectionMessage));
    }

}