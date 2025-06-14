package TCP;

import clase.Candidat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static void server_main() throws IOException {
        int PORT = 8080;

        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Serverul a pornit!");
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Clientul s-a conectat cu serverul!");

                BufferedReader br_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw_out = new PrintWriter(socket.getOutputStream(), true);

                String raspuns = null;
                // eu primesc de la client codul!
                String cod = br_in.readLine();

                int codCautat = Integer.parseInt(cod);
                for(Candidat candidat : Main.listaCandidati) {
                    if(codCautat == candidat.getCodCandidat()){
                        raspuns = candidat.getNume();
                        break;
                    }
                }

                // trimit raspunsul
                pw_out.println(raspuns);

                socket.close();
            }
        }
    }
}
