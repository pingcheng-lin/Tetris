export PATH_TO_FX=~/Desktop/netProgram/hw5/Tetris/src/javafx-sdk-16/lib/
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Client.java LoginController.java GroundController.java Controller.java Form.java
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Client
