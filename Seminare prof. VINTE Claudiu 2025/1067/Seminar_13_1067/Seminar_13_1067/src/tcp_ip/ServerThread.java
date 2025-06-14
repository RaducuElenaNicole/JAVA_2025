package tcp_ip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerThread extends Thread {
    private Socket client = null;
    private static int nrClienti = 0;

    public ServerThread(Socket socket) {
        super("ServerThread");
        this.client = socket;
        this.setName(this.getName() + "-" + ++nrClienti);
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(client.getInputStream());
             DataOutputStream out = new DataOutputStream(client.getOutputStream()))
        {
            System.out.println("Conexiune acceptata de la client: " + client.getRemoteSocketAddress() +
                    " (" + this.getName() + ")");
            String mesajClient = null;
            while(true)
            {
                //1.Primire cerere client
                mesajClient = in.readUTF();

                //2.Procesare cerere client
                System.out.println("MESAJ DE LA CLIENT " + mesajClient +
                        " (" + this.getName() + ")");
                sleep(1000);

                //3.Pregatire raspuns
                String raspuns = "ECHO SERVER: " + mesajClient;

                //4.Trimitere raspuns catre client
                out.writeUTF(raspuns);
            }
        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
              System.out.println("DECONECTARE DE LA CLIENT " + "(" + this.getName() + ")");
        }

    }
}
