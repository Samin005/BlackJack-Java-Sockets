import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final int PORT = 6667;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private Scanner scanner;
    int playerNo = -1;

    public Client() {
        try {
            socket = new Socket("localhost", PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
            playerNo = (int) inputStream.readObject();
            System.out.println("You have joined as Player " + (playerNo+1));
            scanner = new Scanner(System.in);
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void playGame() {
        try {
            while (true) {
                Game game = (Game) inputStream.readObject();
                System.out.println(game.toStringClient(playerNo));

                String command = scanner.nextLine();
                outputStream.writeObject(command);
                outputStream.flush();
                outputStream.reset();

                game = (Game) inputStream.readObject();
                System.out.println(game.toStringClient(playerNo));

                if(game.current_player != playerNo) break;
            }
            getResults();
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void getResults() {
        try {
            System.out.println(inputStream.readObject());
            System.out.println(inputStream.readObject());

            String command = scanner.nextLine();
            outputStream.writeObject(command);
            outputStream.flush();
            outputStream.reset();

            if(command.toLowerCase().startsWith("y")) playGame();
        } catch (IOException | ClassNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void closeConnection() {
        try {
            socket.close();
            inputStream.close();
            outputStream.close();
            scanner.close();
            System.out.println("Client connection closed.");
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.playGame();
        client.closeConnection();
    }
}
