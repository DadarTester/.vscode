import java.io.*;
import java.net.*;
import java.util.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Отправляем имя серверу
            out.println(name + " has joined the chat.");

            // Поток для чтения сообщений от сервера
            new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Чтение сообщений от пользователя и отправка на сервер
            String userInput;
            while ((userInput = scanner.nextLine()) != null) {
                out.println(name + ": " + userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}