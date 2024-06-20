import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 50000;

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado ao servidor em " + host + ":" + port);

            // Envia a primeira mensagem
            System.out.print("Digite a mensagem que deseja enviar: ");
            String message = scanner.nextLine();
            out.println(message);

            // Cria uma thread para receber mensagens do servidor
            Thread receiveThread = new Thread(() -> {
                try {
                    String response;
                    while ((response = in.readLine()) != null) {
                        System.out.println("Mensagem recebida de volta do Servidor: " + response);
                    }
                } catch (IOException e) {
                    System.out.println("Conexão encerrada pelo servidor.");
                }
            });

            receiveThread.start();

            // Envia mensagens ao servidor até que o usuário digite "sair"
            while (true) {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("sair")) {
                    break;
                }
                out.println(input);
            }

            // Fecha a conexão e encerra a thread de recebimento
            socket.close();
            receiveThread.join();

            System.out.println("Desconectado do servidor");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
