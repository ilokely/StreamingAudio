package client;

import java.io.*;
import java.net.*;
import java.util.Vector;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import affichage.FrameClient;
import javazoom.jl.player.advanced.AdvancedPlayer;
import thread.*;
import javax.swing.JOptionPane;
import java.awt.event.*;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;


public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost",1030);
        InputStream data = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(data);
        int listeSize = ois.readInt();
        System.out.println(listeSize+" fichiers existants dans votre serveur");

        String[] multimedia = new String[listeSize];
        // String[] name = new String[listeSize];

        for (int i = 0; i < listeSize; i++) {
            multimedia[i] = (String)ois.readObject();
        }

        FrameClient frameC = new FrameClient(multimedia);
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        System.out.println("choix"+ frameC.getChoix());

        while (frameC.getChoix() == -1) {
            System.out.println("loading");
        }
        dos.writeUTF(multimedia[frameC.getChoix()]);
        System.out.println("execution "+ multimedia[frameC.getChoix()]);

        int nbr = 0;
        int taille = 1024 * 500;
        byte[] bytes = new byte[taille];

        //VIDEO
    byte b;
    JFrame frameVideo = new JFrame("MP4");
    frameVideo.setLocationRelativeTo(null);
    frameVideo.setSize(500,500);
    frameVideo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    EmbeddedMediaPlayerComponent mediaPlayerComponent = new EmbeddedMediaPlayerComponent();

    while (true) {
        DataInputStream stream = new DataInputStream(data);

        if (multimedia[frameC.getChoix()].endsWith(".mp3")) {
            AdvancedPlayer playerMP3 = new AdvancedPlayer(stream);
            if (stream.available() != 0) {
                System.out.println(stream.available() + "bytes");
                playerMP3.play();
            }
            System.out.println("tache achevee");
            playerMP3.close();
            break;
        }

        if (multimedia[frameC.getChoix()].endsWith(".mp4")) {
            File fichier = new File("fichier.mp4");
            b = stream.readByte();

            if (fichier.exists() == true) {
                fichier.delete();
                fichier.createNewFile();
                System.out.println("file "+fichier.getName() +" created");
            }else{
                fichier.createNewFile();
                System.out.println("file "+fichier.getName() +" created");
            }
            Thready myThread = new Thready();
            myThread.start();

            while (true) {
                DataInputStream streamVideo = new DataInputStream(data);
                b = streamVideo.readByte();

                if (nbr != taille - 1) {
                    bytes[nbr] = b;
                    nbr = nbr + 1;
                }else{
                    nbr = 0;
                    if (fichier.exists() == true) {
                        FileOutputStream fileOutputStream = new FileOutputStream(fichier);
                        fileOutputStream.write(bytes);
                        fileOutputStream.close();
                    }else{
                        System.out.println("reessayer");
                    }
                }
            }
        }

        /* if (multimedia[frameC.getChoix()].endsWith(".JPG")) {
        
        } */

    }


        
    }
}
