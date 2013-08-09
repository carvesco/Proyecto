package Connect;

import javax.swing.JLabel;

public class MainClient {

    int port = 4321;
    Client C = new Client();
    String Server = "10.1.70.254";
    
    public boolean sendData(String Mens) {
        return C.sendData(Server, Mens, port);
    }
    
    public void setServer(JLabel Con, String Serv){
        System.out.println("Client connected...");
        //String Serv = JOptionPane.showInputDialog(null, "Ingrese la ip del servidor", "Conectar", JOptionPane.INFORMATION_MESSAGE);
        Server = Serv;
        if(sendData("connect"))
            Con.setText("Conectado con la ip: " + Server);
        else
            Con.setText("No se ha podido conectar");
    }
    
}
