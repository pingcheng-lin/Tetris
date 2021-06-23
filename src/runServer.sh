export PATH_TO_FX=/media/sf_virtualbox/mytetris/src/javafx-sdk-16/lib/
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Client.java LoginController.java GroundController.java Behaviour.java Pattern.java Server.java
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml Server
rm *.class