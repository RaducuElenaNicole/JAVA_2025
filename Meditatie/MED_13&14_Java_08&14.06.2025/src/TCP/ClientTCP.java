package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientTCP {

    public static void main() throws IOException {
        int PORT = 8080;
        String localhost = "localhost";

        try(Socket socket = new Socket(localhost, PORT)){
            BufferedReader bw_in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw_out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scannerC = new Scanner(System.in);
            System.out.print("Introduceti codul candidatului: ");
            String cod = scannerC.nextLine();
            pw_out.println(cod);

            String raspuns = bw_in.readLine();
            System.out.println("Raspunusl de la server este -> numele candidatului cu codul "
                    + cod + " este " + raspuns);
        }
    }
}
