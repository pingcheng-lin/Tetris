import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
import java.io.IOException;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Client extends Application {

    public static DataInputStream input;
    public static DataOutputStream output;

    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static int XMAX = SIZE * 10;
    public static int YMAX = SIZE * 22;
    public static int[][] MESH = new int[XMAX / SIZE][YMAX / SIZE];
    public static Form object;
    public static int score = 0;
    public static int top = 0;
    public static boolean game = true;
    public static Form nextObj = Controller.makeRect();
    public static int linesNo = 0;
    public static Stage stage;
    public static Pane root = new StackPane();
    public static Pane group = new Pane();
    public static Pane group2 = new Pane();
    public static Scene scene2 = new Scene(root, 666, 550);
    public static String enemyName = new String();
    public static String myName = new String();

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket socketClient = new Socket("localhost", 1234);
        input = new DataInputStream(socketClient.getInputStream());
        output = new DataOutputStream(socketClient.getOutputStream());
        launch(args);
        // socketClient.close();


    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);

        Image icon = new Image("img/tetris.png");
        stage.getIcons().add(icon);
        stage.setTitle("Tetris Battle");
        stage.show();
    }
}