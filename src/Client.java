import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
import java.io.IOException;
import java.util.*;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class Client extends Application {

    public static Stage stage;
    public static Pane middleGround = new Pane();
    public static Pane leftGround = new Pane();
    public static Pane rightGround = new Pane();
    public static GridPane root = new GridPane();
    public static Scene scene2 = new Scene(root, 666, 550);
    public static String enemyName = new String();
    public static String myName = new String();
    public static Socket clientSocket;
    public static DataInputStream input; 
    public static DataOutputStream output;
    public static String[] allPatternPool = new String[10];
    public static StringBuilder myPattern = new StringBuilder();
    public static StringBuilder enPattern = new StringBuilder();
    public static Integer patternSeedNumber;
    public static Integer myPatternNumber = 0;
    public static Integer enPatternNumber = 0;
    public static File myObject;
    public static Scanner myReader;

    public static void main(String[] args) throws UnknownHostException, IOException {
        
        myObject = new File("pattern.txt");
        myReader = new Scanner(myObject);
        int i = 0;
        while(myReader.hasNextLine()){
            allPatternPool[i] = myReader.nextLine();
            i++;
        }

        clientSocket = new Socket("localhost", 1234);
        input = new DataInputStream(clientSocket.getInputStream());
        output = new DataOutputStream(clientSocket.getOutputStream());

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);

        Image icon = new Image("img/tetris.png");
        stage.getIcons().add(icon);
        stage.setTitle("Tetris Battle");
        stage.show();
    }
}
