import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientTCP {

    static int PORT = 9090;
    static String localhost = "localhost";

    public static void main() {
        try(Socket socket = new Socket(localhost, PORT)){
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduceti id-ul dorit");
            String cod = scanner.nextLine();

            System.out.println("Introduceti materia dorita");
            String materie = scanner.nextLine();

            out.println(cod);
            out.println(materie);

            String raspuns = in.readLine();
            System.out.println("Raspunsul de la server este: " + raspuns);

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
