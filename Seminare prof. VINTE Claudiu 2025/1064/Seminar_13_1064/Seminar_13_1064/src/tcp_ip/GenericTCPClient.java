package tcp_ip;

import java.io.*;
import java.net.Socket;


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

        try
        {
            System.out.println("Client conectare la server: "+numeServer
            +" port: "+port);
            Socket client = new Socket(numeServer,port);
            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            InputStream inFromServer = client.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(System.in)); // citire de la tastatura
            while(true)
            {
                //1. CREARE CERERE PT SERVER
                String mesaj = reader.readLine();
                if(mesaj.trim().toLowerCase().equals("exit"))
                {
                    out.close();
                    in.close();
                    client.close();
                    System.out.println("Iesire din program");
                    System.exit(0);
                }
                //2. Trimitere cerere catre server
                out.writeUTF(mesaj);
                //3. Preluare raspuns de la server
                String raspuns = in.readUTF();
                //4. Procesare raspuns de la server
                System.out.println(raspuns);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
