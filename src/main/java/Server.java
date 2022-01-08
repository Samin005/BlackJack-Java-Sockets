import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int SERVER_PORT = 6667;
    private ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    Game game;

    private void initialize() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            socket = serverSocket.accept();
            inputStream = new ObjectInputStream(socket.getInputStream());
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

    }

    private void startGame() {
        game = new Game();
        System.out.println(game);
        try {
            while(true) {
                String msg = (String) inputStream.readObject();
                System.out.println(msg);
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }  catch (ClassNotFoundException classNotFoundException) {
            System.out.println(classNotFoundException.getMessage());
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.initialize();
        server.startGame();
    }
}
