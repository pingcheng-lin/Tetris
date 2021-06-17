import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController extends Client {

    @FXML
    private TextField nameField;

    @FXML
    private Text nameOfGame;

    @FXML
    private Button submitButton;

    @FXML
    private ImageView ruSword;

    @FXML
    private ImageView rdSword;

    @FXML
    private ImageView ldSword;

    @FXML
    private ImageView luSword;

    @FXML
    private ImageView monkey;

    @FXML
    public void submitName(ActionEvent event) throws Exception {
        myName = nameField.getText();
        System.out.println(myName);
        if (myName.equals("a")) {
            submitButton.setDisable(true);
            submitButton.setText("Wait");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Runnable updater = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                GroundController.initialize();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    try {
                        // output.writeUTF(myName);
                        // enemyName = input.readUTF();

                        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                        middleGround = FXMLLoader.load(getClass().getResource("GroundView.fxml"));
                        root.getChildren().addAll(middleGround, leftGround, rightGround);
                        Platform.runLater(updater);
                    } catch (Exception e) {
                    }
                }
            });
            thread.setDaemon(true);
            thread.start();
        }
    }
}
