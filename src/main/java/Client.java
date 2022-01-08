import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final int PORT = 6667;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Scanner scanner;

    public Client() {
        try {
            socket = new Socket("localhost", PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            scanner = new Scanner(System.in);
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    private void makeMove() {
        try {
            while (true) {
                String command = scanner.nextLine();
                outputStream.writeObject(command);
                outputStream.flush();
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.makeMove();
    }
}
