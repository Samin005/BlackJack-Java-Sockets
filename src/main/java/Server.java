import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private int SERVER_PORT = 6667;
    private ServerSocket serverSocket;
    private ArrayList<Socket> sockets = new ArrayList<>();
    private ArrayList<ObjectInputStream> inputStreams = new ArrayList<>();
    private ArrayList<ObjectOutputStream> outputStreams = new ArrayList<>();
    Game game;

    private void initialize() {
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            game = new Game();
            System.out.println("Server initialized. Waiting for players...");
            while(game.players_connected < game.TOTAL_PLAYERS - 2) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                inputStreams.add(inputStream);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStreams.add(outputStream);
                outputStream.writeObject(game.players_connected);
                outputStream.flush();
                game.players_connected++;
                System.out.println("Player " + game.players_connected + " has joined!");
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
    }

    private void startGame() {
        System.out.println("\nStarting game...\n");
        try {
            while(true) {
                for(int i = 0; i < sockets.size(); i++) {
                    outputStreams.get(i).writeObject(game);
                    outputStreams.get(i).flush();
                    System.out.println(game);

                    String command = (String) inputStreams.get(i).readObject();
                    System.out.println("Player command: "+ command);

                    game.stay();
                    if(game.current_player == game.AI_PLAYER) game.nextRound();
                }
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
