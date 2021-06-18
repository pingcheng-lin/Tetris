import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.io.*;
import java.lang.String;

class Server {
    static Map<Socket, Socket> mapEnemy = new HashMap<>();
    static Vector<Socket> players = new Vector<Socket>();
    static Vector<String> playersName = new Vector<String>();
    static int countPlayer = 0;

    public static void main(String args[]) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234); // create a server socket

        while (true) {
            System.out.println("Waiting player...");
            Socket connection = serverSocket.accept(); // establish connection and waits for the client
            System.out.println("A Connection Success");
            countPlayer++;
            if (countPlayer == 2)
                while (true)
                    ;
            slaveForClientConnection wr = new slaveForClientConnection(connection); // socket to socket connection
            Thread th = new Thread(wr);
            th.start();
        }
    }
}

class WaitingRoom implements Runnable {
    protected Socket player1;
    protected Socket player2;
    protected DataInputStream inputOfPlayer1;
    protected DataInputStream inputOfPlayer2;
    protected DataOutputStream outputOfPlayer1;
    protected DataOutputStream outputOfPlayer2;
    protected ObjectOutputStream outputOfObjectOfPlayer1;
    protected ObjectOutputStream outputOfObjectOfPlayer2;
    public static Boolean gameIsOpen = false;

    WaitingRoom() {

    }

    public void run() {
        while (Server.players.size() == 2) {
            // Start the game when 2 player are ready
            player1 = Server.players.get(0);
            player2 = Server.players.get(1);
            Server.mapEnemy.put(player1, player2);
            Server.mapEnemy.put(player2, player1);
            try {
                outputOfObjectOfPlayer1 = new ObjectOutputStream(player1.getOutputStream());
                outputOfObjectOfPlayer2 = new ObjectOutputStream(player2.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            synchronized (gameIsOpen) {
                gameIsOpen = true;
            }
            while (true) { // while gameIsOver
                // 負責一直送方塊給客戶端
                Form aPattern = Controller.makeRect();
                try {
                    outputOfObjectOfPlayer1.writeObject(aPattern);
                    outputOfObjectOfPlayer2.writeObject(aPattern);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class slaveForClientConnection implements Runnable {
    private Socket connection; // player's own connection
    private DataInputStream input; // input from the right client
    private DataOutputStream output; // output to opponent client

    private DataOutputStream outputToOpponent; // output to opponent client

    public slaveForClientConnection(Socket connection) throws IOException {
        this.connection = connection;
        input = new DataInputStream(connection.getInputStream());
        output = new DataOutputStream(connection.getOutputStream());
    }

    public void run() {
        try {
            System.out.println("Wait player name...");
            String myName = input.readUTF();//read name
            System.out.println("Player name: " + myName + " login.");

            if (!Server.playersName.contains(myName)) {
                Server.playersName.add(myName);
                System.out.println("Duplicate: " + myName);
            }
            // 接收動作
            synchronized (WaitingRoom.gameIsOpen) {
                while (!WaitingRoom.gameIsOpen)
                    ;
            }

            outputToOpponent = new DataOutputStream(Server.mapEnemy.get(connection).getOutputStream());
            while (true) {
                int behaviorToOpponent = input.readInt();
                outputToOpponent.writeInt(behaviorToOpponent); // send to opponent
                synchronized (WaitingRoom.gameIsOpen) {
                    if (behaviorToOpponent == 5)
                        WaitingRoom.gameIsOpen = false;
                    if (!WaitingRoom.gameIsOpen)
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}