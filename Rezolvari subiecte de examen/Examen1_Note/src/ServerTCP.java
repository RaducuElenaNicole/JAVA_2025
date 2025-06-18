
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    static int PORT = 9090;

    public static void main() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            Socket socket = serverSocket.accept();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String raspuns = null;

            String cod = in.readLine();
            String materie = in.readLine();
            int codCautat = Integer.parseInt(cod);

            for(var elm: Main.listaStudetiTXT){
                if(elm.getIdStudent() == codCautat && elm.getMaterie().equals(materie)){
                    raspuns = String.valueOf(elm.getNota());
                }
            }

            out.println(raspuns);
            socket.close();

        }
    }
}
