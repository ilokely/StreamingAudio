package thread; 

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.*;
import java.io.File;

public class Thready extends Thread {
    @Override
    public void run() {
         // TODO Auto-generated method stub
        JFrame frame = new JFrame("Video");
        frame.setLocationRelativeTo(null);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        EmbeddedMediaPlayerComponent mediaplayerComponent = new EmbeddedMediaPlayerComponent();
        File src = new File("./Multimedia/video.mp4");
        try {
            this.sleep(500);
        } catch (Exception e) {
            // TODO: handle exception
        }
        if (src.exists() == true) {
           try {
            frame.add(mediaplayerComponent);
            frame.setVisible(true);
            mediaplayerComponent.mediaPlayer().media().play(src.getPath());
            System.out.println(src.length());
           } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
           }
            
        }
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int reponse = JOptionPane.showConfirmDialog(frame, "Quit?", "confirm", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if (reponse == JOptionPane.YES_OPTION) {                
                if(src.delete() == true){
                    System.out.println(src.getName()+ "deleted");
                    src.deleteOnExit();
                }           
                System.exit(0);     
            
                }
            }
        });
    }
}

