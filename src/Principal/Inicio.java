package Principal;

import Clases.*;
import Interfaz.Juego;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
/*
*
*@author Freddy Vanegas
*/
public class Inicio {

    public static void main(String[] args) throws FileNotFoundException, UnsupportedAudioFileException, IOException, InterruptedException {
        Juego vista = new Juego();
        Efecto efecto = new Efecto(vista);
        ControlJuego control = new ControlJuego(vista, efecto.BotonBase, efecto.BotonMapa, efecto.Muestra);
        control.IniciarJuego();
        Sonido sonido = new Sonido();
        sonido.Sonido("gametrack.wav");
        vista.setVisible(true);
    }

}
