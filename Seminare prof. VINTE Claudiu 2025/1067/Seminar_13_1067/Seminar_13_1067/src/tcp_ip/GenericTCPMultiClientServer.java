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

        try(ServerSocket server = new ServerSocket(port))
        {
            System.out.println("ASTEPT CONEXIUNI CLIENT PE PORTUL: " + port);
            while(true)
            {
                new ServerThread(server.accept()).start();

            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("CONEXIUNE ESUATA PE PORTUL: " + port);
            System.exit(-1);
        }
    }
}
