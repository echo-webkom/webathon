package Model.soundsystem;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.*;

public class chessGridLauncher {
    public static void main(String[] args) {
        soundMakerwaw backgroundSound = new soundMakerwaw("mario-theme.wav");
        backgroundSound.loop();
        SwingUtilities.invokeLater(() -> {

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();

            JFrame frame = new JFrame("Ultimate Chess Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setUndecorated(true);
            
            frame.add(new MainGamePanel()); 

            if (gd.isFullScreenSupported()) {
                gd.setFullScreenWindow(frame);
            } else {

                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
