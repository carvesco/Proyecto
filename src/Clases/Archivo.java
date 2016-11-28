package Clases;

import java.io.*;
import javax.swing.JOptionPane;
/**
 *
 * @author Freddy Nicolay Vanegas
 */
public class Archivo {

    public File archivo;
    public FileReader LeerArchivo;
    public FileWriter EscribirArchivo;
    public BufferedReader LeerBufer;
    public BufferedWriter EscribirBufer;
    public PrintWriter Imprimir;

    public void concatenar(String ruta, String texto) {
        String temp = leer(ruta);
        temp = temp + texto;
        crearTxt(ruta, temp);
    }

    public void crearTxt(String ruta, String texto) {
        try {
            archivo = new File(ruta);
            EscribirArchivo = new FileWriter(archivo);
            EscribirBufer = new BufferedWriter(EscribirArchivo);
            Imprimir = new PrintWriter(EscribirBufer);

            Imprimir.write(texto);

            Imprimir.close();
            EscribirBufer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error \n" + e.getMessage(), "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String leer(String ruta) {
        try {
            archivo = new File(ruta);
            LeerArchivo = new FileReader(archivo);
            LeerBufer = new BufferedReader(LeerArchivo);
            String l = "";
            String aux;
            while (true) {
                aux = LeerBufer.readLine();
                if (aux != null) {
                    l = l + aux;
                } else {
                    break;
                }
            }
            LeerBufer.close();
            return l;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error \n" + e.getMessage(), "Mensaje de Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public String leerGrafico() {
        javax.swing.JFileChooser j = new javax.swing.JFileChooser();
        j.showOpenDialog(j);
        try {
            String path = j.getSelectedFile().getAbsolutePath();
            String lectura = "";
            archivo = new File(path);
            try {
                FileReader fr = new FileReader(archivo);
                BufferedReader br = new BufferedReader(fr);
                String aux;
                while ((aux = br.readLine()) != null) {
                    lectura = lectura + aux;
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error \n" + e.getMessage(), "Mensae de Error", JOptionPane.ERROR_MESSAGE);
            }
            return lectura;
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Has Seleccionado Cerrar el Programa", "Saliendo", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
        return null;
    }
    
}
