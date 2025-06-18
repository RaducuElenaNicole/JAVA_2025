import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class ServerUDP {
    static int PORT = 9091;

    public static void main(String[] args) throws SocketException {
        try(DatagramSocket socket = new DatagramSocket(PORT)){
            System.out.println("Serverul este pornit");
            while(true) {
                byte[] buffer = new byte[200];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);


                String codPrimit = new String(request.getData(), 0, request.getLength());
                System.out.println("Am primit");
                String raspuns = null;
                try {
                    int cod = Integer.parseInt(codPrimit);
                    for (var elm : Main.listaStudetiTXT) {
                        if (elm.getIdStudent() == cod) {
                            raspuns = elm.getMaterie();
                            break;
                        }
                    }
                    byte[] raspunsInBytes = raspuns.getBytes();
                    DatagramPacket raspuns_packet = new DatagramPacket(raspunsInBytes, raspunsInBytes.length, request.getAddress(), request.getPort());
                    socket.send(raspuns_packet);
                } catch (NumberFormatException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
