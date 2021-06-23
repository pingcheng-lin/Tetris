import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.io.*;
import java.lang.String;

class Server {
    static Map<Socket, Socket> mapEnemy = new HashMap<>();
    static Map<String, String> mapEnemyName = new HashMap<>();
    static Vector<Socket> players = new Vector<Socket>();
    static Vector<String> playersName = new Vector<String>();
    // players information
    static int countPlayer = 0;
    // How many player connected

    public static void main(String args[]) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234); // create a server socket

        Thread wr  = new Thread(new WaitingRoom());
        // gaming monitor, handling the game start
        wr.start();

        while (true) {
            System.out.println("Waiting player...");
            Socket connection = serverSocket.accept(); // establish connection and waits for the client
            System.out.println("A Connection Success");
            countPlayer++;

            slaveForClientConnection sc = new slaveForClientConnection(connection); // socket to socket connection
            Thread th = new Thread(sc);
            th.start();
            if (countPlayer == 2){
                System.out.println("Two players are here, Game is going to start");
            // start a game
                while (true)
                    ;
            }
        }
    }
}

class WaitingRoom implements Runnable {
    protected Socket player1;
    protected Socket player2;
    protected String player1Name;
    protected String player2Name;

    public static Boolean gameIsOpen = false;
    public static Integer gameingSeed = 0;

    WaitingRoom() {
        // constructor
    }

    public void run() {
        System.out.println("Hello, gaming room is here");
        while (Server.players.size() < 2)
            ;//System.out.println("Not yet enough players to start the game");
        System.out.println("Gaming field initializing");
    
        // Start the game when 2 player are ready
        // First now, 2 players is limited
        player1 = Server.players.get(0);
        player2 = Server.players.get(1);
        // get Socket and put them into hashmap
        Server.mapEnemy.put(player1, player2);
        Server.mapEnemy.put(player2, player1);

        player1Name = Server.playersName.get(0);
        player2Name = Server.playersName.get(1);
        // get name and put them into hashmap
        Server.mapEnemyName.put(player1Name, player2Name);
        Server.mapEnemyName.put(player2Name, player1Name);
            
        try{
            gameingSeed = (int)(Math.random()*10);
            // generate gaming seed which is for select the order of the pattern
            System.out.println(gameingSeed);
            DataOutputStream outputOfPlayer1 = new DataOutputStream(player1.getOutputStream());
            DataOutputStream outputOfPlayer2 = new DataOutputStream(player2.getOutputStream());
            outputOfPlayer1.write(gameingSeed);
            outputOfPlayer1.flush();
            outputOfPlayer2.write(gameingSeed);
            outputOfPlayer2.flush();
            // tell players the gaming seed

            gameIsOpen = true;
            // game initialization finished
        }catch (IOException e){
            System.out.println("is it start?");
        }
        
    }
}

class slaveForClientConnection implements Runnable {
    private Socket connection; // player's own connection
    private DataInputStream input; // input from the right client
    private DataOutputStream output; // output to opponent client

    private DataOutputStream outputToOpponent; // output to opponent client
    // for show the enemy playing scene
    public slaveForClientConnection(Socket connection) throws IOException {
        this.connection = connection;
        input = new DataInputStream(connection.getInputStream());
        output = new DataOutputStream(connection.getOutputStream());
    }

    public void run() {
        try {
            String myName = input.readUTF();//read name
            System.out.println("Player name: " + myName + " login.");
            Server.players.add(connection);

            if (!Server.playersName.contains(myName)) {
                // check dup player
                Server.playersName.add(myName);
                System.out.println(myName+" is in players list now");
            }
            // 接收動作
            while (!WaitingRoom.gameIsOpen)
                System.out.print("");
            // lock until game has setup, the variable is gameIsOpen

            System.out.println("My name "+ myName +", enemy name " + Server.mapEnemyName.get(myName));
            output.writeUTF(Server.mapEnemyName.get(myName));
            output.flush();
            System.out.println("ok");

            outputToOpponent = new DataOutputStream(Server.mapEnemy.get(connection).getOutputStream());
            // Because the players information is stored by waiting room, just use method get(myargument)
            while (true) {
                int behaviorToOpponent = input.readInt();
                // receive my move of keyboard
                outputToOpponent.writeInt(behaviorToOpponent);
                // write the behaviour to opponent
                outputToOpponent.flush();

                if (behaviorToOpponent == 15){
                    WaitingRoom.gameIsOpen = false;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}