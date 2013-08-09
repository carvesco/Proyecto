package Connect;

import java.net.*;
import java.io.*;

public class Client {

    int c, longCad;
    Socket s1 = null;
    OutputStream s1out;

    public boolean sendData(String server, String sendString, int port) {
        try {
            s1 = new Socket(server, port);
            s1out = s1.getOutputStream();
            longCad = sendString.length();
            for (int i = 0; i < longCad; i++) {
                s1out.write((int) sendString.charAt(i));
            }
            s1out.close();
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
    }
}
