package tcp_ip;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;


public class ServerThread extends Thread {
    private Socket client = null;
    private static int nrClienti = 0;

    public ServerThread(Socket socket) {
        super("ServerThread");
        this.client = socket;
        this.setName(this.getName() + "-" + ++nrClienti);
        //this.setName("serverThread" ++nrClienti);
    }

    @Override
    public void run() {

        // TODO
        try (DataInputStream in =
                     new DataInputStream(client.getInputStream());
             DataOutputStream out =
                     new DataOutputStream(client.getOutputStream())
             )
        {
            System.out.println("Conexiune acceptata de la clientul "
            +client.getRemoteSocketAddress()+" ("+this.getName()+")");
            String mesajPrimit;
            while(true)
            {
                //1. Preluare cerere client
                mesajPrimit = in.readUTF();
                //2. Procesare cerere client
                System.out.println(mesajPrimit+" ("+this.getName()+")");
                sleep(1000);
                //3. Pregatire Raspuns client
                String raspuns = "$ECHO SERVER: " +mesajPrimit;
                //4. Trimitere raspuns client
                out.writeUTF(raspuns);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
