/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/*
*
*@author Freddy Vanegas
*/
public class Sonido {

    public Sonido() {
    }
    
        public void Sonido(String pista) throws FileNotFoundException, IOException, InterruptedException{
        String File = pista;
        InputStream in = new FileInputStream(File);
        AudioStream audioStream = new AudioStream(in);
        AudioPlayer.player.start(audioStream);
        if("soundtrack.wav".equals(pista)){
        Thread.sleep(21900);   
        audioStream.reset();
        }
        
    }
    
}
