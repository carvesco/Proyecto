package App;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;

public class SoundFX {

    BasicPlayer player;
    Clip soundtrack, sonido;
    boolean Active = true;

    public void turnOffFX(boolean R) {
        Active = !R;
    }

    public void turnOff(boolean R) {
        try {
            if (R) {
                player.pause();
            } else {
                player.resume();
            }
        } catch (Exception e) {
        }
    }

    public void playSoundtrack() {
        try {
            player = new BasicPlayer();
            BasicController control = (BasicController) player;
            control.open(new File("./sounds/soundtrack.mp3"));
            control.play();
            control.setGain(0.85);
            control.setPan(0.0);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void play(String Sound) {
        if (Active) {
            try {
                sonido = AudioSystem.getClip();
                sonido.open(AudioSystem.getAudioInputStream(new File("./sounds/" + Sound)));
                sonido.start();
                while (sonido.isRunning()) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
            }
        }
    }
}
