import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.io.*;
import java.lang.String;

class Server {
    static Map<String, String> mapEnemy = new HashMap<>();
    static Vector<String> players = new Vector<String>();
    static int countPlayer = 0;

    public static void main(String args[]) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234); // create a server socket

        while (true) {
            System.out.println("Waiting player...");
            Socket connection = serverSocket.accept(); // establish connection and waits for the client
            System.out.println("A Connection Success");
            if(countPlayer == 2)
                while(true);
            WaitingRoom wr = new WaitingRoom(connection); // socket to socket connection
            Thread th = new Thread(wr);
            th.start();
        }
    }
}

class WaitingRoom implements Runnable {
    private Socket connection; // player's own connection
    private DataInputStream input;
    private DataOutputStream output;

    public WaitingRoom(Socket connection) throws IOException {
        this.connection = connection;
        input = new DataInputStream(connection.getInputStream());
        output = new DataOutputStream(connection.getOutputStream());
    }

    public void run() {
        try {
            System.out.println("Wait player name...");
            String myname = input.readUTF();
            System.out.println("Player name: " + myname + " login.");

            if(!Server.players.contains(myname)) {
                Server.players.add(myname);
                System.out.println("Duplicate: " + myname);
            }

            output.writeUTF("QWE");
            System.out.println("finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}