package Connect;



import java.net.*;
import java.io.*;

public class Server {

    public ServerSocket s = (ServerSocket) null;
    private Socket s1 = null;
    private int c;
    private InputStream sIn;
    public String Mens = "", cliente = "";
    
    public String getMensage(){
        if(Mens.length() > 0){
            String N = Mens;
            Mens = "";
            return N;
        }
        else{
            return "";
        }
        
    }
    
    public void start(int port) {
        try {
            s = new ServerSocket(port, 300);
        } catch (IOException e) {
            System.out.println(e);
        }

        while (true) {
            try {
                s1 = s.accept();
                cliente = s1.getLocalAddress() + "";
                sIn = s1.getInputStream();
                String N = "";
                while ((c = sIn.read()) != -1) {
                    N += (char) c;
                }
                s1.close();
                if(N.equals("connect"))
                    Mens = cliente;
                else
                    Mens = N;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}