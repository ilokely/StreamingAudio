package serveur;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javazoom.jl.player.Player;

public class Serveur {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1030);
        System.out.println("En attente d'un client");
        Socket socket = serverSocket.accept();
        OutputStream outputStream = socket.getOutputStream();

    
//source Multimedia 
        File multimedia = new File("./Multimedia");
        File[] liste = multimedia.listFiles();
        ObjectOutputStream oos = new ObjectOutputStream(outputStream);
//Mandefa taille liste vers client
        oos.writeInt(liste.length);

        for(int i = 0; i < liste.length ; i++){
            oos.writeObject(liste[i].getName());
            System.out.println(liste[i].getName());
        }

        InputStream inputStream = socket.getInputStream();
        DataInputStream dis = new DataInputStream(inputStream);
        String titre = dis.readUTF();
        File ficher = new File(titre);
        String source = "./Multimedia/";

        FileInputStream fileInputStream = new FileInputStream(source+ficher);
        DataOutputStream dos = new DataOutputStream(outputStream);

        while (true) {
            byte[] array = new byte[1000];
            fileInputStream.read(array);
            dos.write(array);
            dos.flush();
        }
        
        
    }
}