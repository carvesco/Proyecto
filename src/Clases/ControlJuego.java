package Clases;

import Interfaz.Clasificacion;
import Interfaz.Juego;
import Principal.Inicio;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/*
*
*@author Freddy Vanegas
*/
public class ControlJuego implements ActionListener {

    private Juego juego;
    public JButton BotonBase[];
    public JButton BotonMapa[];
    public JButton BotonMuestra[];
    public ControlMovimiento cm;
    private String[] args;
    
    int x,y;
    Window win = new Window(juego);

    public ControlJuego(Juego juego, JButton BotonBase[], JButton BotonMapa[], JButton BotonMuestra[]) {
        this.juego = juego;
        this.BotonBase = BotonBase;
        this.BotonMapa = BotonMapa;
        this.BotonMuestra = BotonMuestra;
        this.juego.setLocationRelativeTo(null);
    }

    public void IniciarJuego() {
        this.juego.jEmpezar.setActionCommand("Empezar");
        this.juego.jReiniciar.setActionCommand("Reiniciar");
        this.juego.jSalir.setActionCommand("Salir");
        this.juego.jClasificacion.setActionCommand("Clasificacion");

        this.juego.jEmpezar.addActionListener(this);
        this.juego.jReiniciar.addActionListener(this);
        this.juego.jSalir.addActionListener(this);
        this.juego.jClasificacion.addActionListener(this);
        
        this.juego.PanelJuego.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                x = evt.getX();
                y = evt.getY();
            }
        });
        this.juego.PanelJuego.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
    }
    
    private void formMouseDragged(java.awt.event.MouseEvent evt) {
        Point point = MouseInfo.getPointerInfo().getLocation();
        this.juego.setLocation(point.x - x, point.y - y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        try{
            switch (cmd) {
                case "Empezar":
                    String nombre = JOptionPane.showInputDialog(null, "Ingrese su Nombre", "Nombre del Usuario", JOptionPane.INFORMATION_MESSAGE);
                    int tamaño = nombre.length();
                    if (!nombre.equalsIgnoreCase("") && tamaño > 1) {
                        if(!"Computadora".equals(nombre)){
                            this.juego.setTitle("Batalla Naval-" + nombre);
                            cm = new ControlMovimiento(juego, BotonBase, BotonMapa, BotonMuestra);
                            cm.Iniciar();
                            this.juego.jEmpezar.setEnabled(false);
                        }else{
                            JOptionPane.showMessageDialog(null, "El nombre Computadora es una palabra reservada","Mensaje de Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    break;
                case "Configuracion":             
                    break;
                case "Reiniciar":
                    this.juego.dispose();
                    Inicio.main(args);
                    break;
                case "Clasificacion":
                    Clasificacion clasificacion = new Clasificacion(juego, true);
                    Puntajes puntajes = new Puntajes(clasificacion);
                    puntajes.Iniciar();
                    clasificacion.setTitle("Puntuaciones");
                    clasificacion.setLocationRelativeTo(null);
                    clasificacion.setVisible(true);
                    break;
                case "Salir":
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }catch(NullPointerException ex){} catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(ControlJuego.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlJuego.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControlJuego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
