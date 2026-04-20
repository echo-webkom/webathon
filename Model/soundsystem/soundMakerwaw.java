package Model.soundsystem;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
public class soundMakerwaw {

    private Clip clip;

    // Hentet fra: https://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
    // hentet 23.04.2024, svart av Bilesh Ganguly, modifisert videre med hjelp av medstudent Jesper Hammer

    public soundMakerwaw(String filename) {
                try {
                    this.clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            soundMakerwaw.class.getResourceAsStream("/sounds/" + filename));
                    this.clip.open(inputStream);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        

        public void play(){
            this.clip.setFramePosition(0);
            this.clip.start();
        }

        public void loop(){
            this.clip.loop(-1);
            this.play();
        }

        public void stop(){
            this.clip.stop();
        }
    
    
    
    
    
    }

