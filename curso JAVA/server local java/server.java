import java.io.*;
import java.net.*;

public class TCPServer {

    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 50000;

        try (ServerSocket serverSocket = new ServerSocket(port, 50, InetAddress.getByName(host))) {
            System.out.println("Servidor na escuta em " + host + ":" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado de " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());

                // Cria uma nova thread para lidar com o cliente
                new Thread(new ClientHandler(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Mensagem recebida: " + message);
                out.println(message); // Echo the message back to the client
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("Cliente desconectado");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
