export PATH_TO_FX=~/Desktop/netProgram/hw5/Tetris/src/javafx-sdk-16/lib/
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Controller.java Server.java Form.java GroundController.java
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Server
rm *.class