package Connect;

import javax.swing.JLabel;

public class MainServer {

    final int port = 4321;
    final Server S = new Server();
    public String client = "", mens = "", chat = "";
    public boolean win = false;
    public int cX = -1, cY = -1, Enemy = 5, aX = -1, aY = -1;
    public boolean x = true, Ready = false;

    public void start(JLabel Con) {
        System.out.println("Server Started...");
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    S.start(port);
                }
            }).start();
        } catch (Exception e) {
        }

        while (x) {
            client = S.getMensage();
            if (client.length() > 0) {
                client = client.substring(1);
                x = false;
            }
        }
        Con.setText("Se ha conectado el cliente: " + client);
        x = true;
        while (x) {
            mens = S.getMensage();
            if (mens.length() > 0) {
                if (mens.equals("win")) {
                    win = true;
                } else {
                    if (mens.substring(0, 1).equals("C")) {
                        String C[] = mens.substring(1).split("-");
                        cX = Integer.parseInt(C[0]);
                        cY = Integer.parseInt(C[1]);
                    } else {
                        if (mens.substring(0, 1).equals("X")) {
                            chat = client + ": " + mens.substring(1);
                        } else {
                            if (mens.equals("R")) {
                                Ready = true;
                                System.out.println("ready");
                            } else {
                                if (mens.substring(0, 1).equals("E")) {
                                    Enemy = Integer.parseInt(mens.substring(1));
                                }
                                else{
                                    if (mens.substring(0, 1).equals("A")) {
                                        mens = mens.substring(1);
                                        String att[] = mens.split("-");
                                        aX = Integer.parseInt(att[0]);
                                        aY = Integer.parseInt(att[1]);
                                    }
                                    else{
                                        System.out.println(mens);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Terminó la conexión");
    }
}
