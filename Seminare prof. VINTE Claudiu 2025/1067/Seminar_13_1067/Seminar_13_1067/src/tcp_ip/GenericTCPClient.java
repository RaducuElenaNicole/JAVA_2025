package tcp_ip;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class GenericTCPClient {
    public static void main(String[] args) {
        // "localhost" este numele asociat adresei de IP 127.0.0.1
        if (args.length != 2) {
            System.err.println("Utilizare: java GenericTCPClient " +
                    "<nume server> <port>");
            System.exit(-1);
        }

        String numeServer = args[0];
        int port = Integer.valueOf(args[1]);

        try{
            //INCERCARE CONECTARE LA SERVER
            System.out.println("Conectare la server " + numeServer + " port: " + port);
            Socket client = new Socket(numeServer,port);
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);
            InputStream inputFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inputFromServer);

            BufferedReader linie = new BufferedReader(new InputStreamReader(System.in));
            while(true)
            {
                //1.Creare cerere client
                String mesaj = linie.readLine();
                if(mesaj.equalsIgnoreCase("exit"))
                {
                    out.close();
                    in.close();
                    client.close();
                    System.exit(0);
                }

                //2.Trimitere cerere catre server
                out.writeUTF(mesaj);

                //3.Primesc raspuns de la server
                String raspuns = in.readUTF();

                //4.Procesare raspuns server
                System.out.println("Raspuns: " + raspuns);

            }
        }
        catch (Exception e) {
//            throw new RuntimeException(e);
            System.out.println("CONEXIUNEA LA SERVER ESUATA!");
            System.exit(-1);
        }

    }
}
