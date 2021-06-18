import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.*;
import java.io.*;
import java.io.IOException;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Client extends Application {

    public static DataInputStream input;
    public static DataOutputStream output;

    public static Stage stage;
    public static Pane middleGround = new Pane();
    public static Pane leftGround = new Pane();
    public static Pane rightGround = new Pane();
    public static GridPane root = new GridPane();
    public static Scene scene2 = new Scene(root, 666, 550);
    public static String enemyName = new String();
    public static String myName = new String();

    public static void main(String[] args) throws UnknownHostException, IOException {
        // Socket socketClient = new Socket("localhost", 1234);
        // input = new DataInputStream(socketClient.getInputStream());
        // output = new DataOutputStream(socketClient.getOutputStream());
        launch(args);
        // socketClient.close();

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
