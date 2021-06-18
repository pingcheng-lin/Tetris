import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
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
                        root.add(leftGround, 0, 0);
                        root.add(middleGround, 1, 0);
                        root.add(rightGround, 2, 0);
                        ColumnConstraints aa = new ColumnConstraints();
                        ColumnConstraints bb = new ColumnConstraints();
                        ColumnConstraints cc = new ColumnConstraints();
                        aa.setPercentWidth(250);
                        aa.setHgrow(Priority.ALWAYS);
                        root.getColumnConstraints().add(aa);
                        bb.setPercentWidth(166);
                        bb.setHgrow(Priority.ALWAYS);
                        root.getColumnConstraints().add(bb);
                        cc.setPercentWidth(250);
                        cc.setHgrow(Priority.ALWAYS);
                        root.getColumnConstraints().add(cc);
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
