
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class ServerTCPMultithreading {
    static int PORT = 9090;

    public static void main() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(PORT)){
            while(true) {
            Socket socket = serverSocket.accept();
                new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         PrintWriter out = new PrintWriter(socket.getOutputStream(), true)){
                        String raspuns = null;
                        String cod = in.readLine();
                        String materie = in.readLine();
                        int codCautat = Integer.parseInt(cod);
                        for (var elm : Main.listaStudetiTXT) {
                            if (elm.getIdStudent() == codCautat && Objects.equals(elm.getMaterie(), materie)) {
                                raspuns = String.valueOf(elm.getNota());
                            }
                        }

                        out.println(raspuns);
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }

        }
    }
}
