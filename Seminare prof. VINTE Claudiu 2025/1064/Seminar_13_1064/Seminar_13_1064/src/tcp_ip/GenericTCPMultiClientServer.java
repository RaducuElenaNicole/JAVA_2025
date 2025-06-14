package tcp_ip;

import java.io.IOException;
import java.net.ServerSocket;

public class GenericTCPMultiClientServer {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Utilizare: java GenericTCPMultiClientServer <port>");
            System.exit(-1);
        }

        int port = Integer.valueOf(args[0]);

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Asteapta conexiuni clienti " +
                    "la portul "+serverSocket.getLocalPort());
            while(true)
            {
                // Accepta conexiune client pe un thread nou
                new ServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            //throw new RuntimeException(e);
            System.out.println("Conxiune esuata pe portul "+
                    port);
            System.exit(-1);
        }

    }
}
