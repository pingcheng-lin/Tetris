export PATH_TO_FX=/media/sf_virtualbox/mytetris/javafx-sdk-11.0.2/lib/
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Client.java LoginController.java GroundController.java Controller.java Form.java
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Client