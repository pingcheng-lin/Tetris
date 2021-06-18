import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class GroundController extends Client {

    @FXML
    private Label myID;

    @FXML
    private Label myHit;

    @FXML
    private static Label myLine;

    @FXML
    private static Label myScore;

    @FXML
    private Label enemyID;

    @FXML
    private Label enemyHit;

    @FXML
    private Label enemyLine;

    @FXML
    private static Label enemyScore;

    public static final int MOVE = 25;
    public static final int SIZE = 25;
    public static int XMAX = SIZE * 10;
    public static int YMAX = SIZE * 22;

    public static int[][] leftArray = new int[XMAX / SIZE][YMAX / SIZE];
    public static int[][] rightArray = new int[XMAX / SIZE][YMAX / SIZE];

    public static Form leftObject;
    public static int leftScoreNum = 0;
    public static int leftTop = 0;
    public static boolean leftGame = true;
    public static Form leftNextObj = Controller.makeRect();
    public static int leftLinesNum = 0;

    public static Form rightObject;
    public static int rightScoreNum = 0;
    public static int rightTop = 0;
    public static boolean rightGame = true;
    public static Form rightNextObj = Controller.makeRect();
    public static int rightLinesNum = 0;

    static void initialize() throws Exception {

        for (int[] a : leftArray) {
            Arrays.fill(a, 0);
        }
        for (int[] a : rightArray) {
            Arrays.fill(a, 0);
        }

        // left player data
        Label myNameText = new Label(myName);
        myNameText.setStyle("-fx-font: 20 Arial;");
        myNameText.setLayoutX(25);
        myNameText.setLayoutY(220);
        Label leftLine = new Label("");
        leftLine.setStyle("-fx-font: 20 Arial;");
        leftLine.setLayoutX(25);
        leftLine.setLayoutY(425);
        Label leftScore = new Label("");
        leftScore.setStyle("-fx-font: 20 Arial;");
        leftScore.setLayoutX(25);
        leftScore.setLayoutY(525);
        middleGround.getChildren().addAll(myNameText, leftLine, leftScore);

        // right player data
        Label enemyNameText = new Label(enemyName);
        enemyNameText.setStyle("-fx-font: 20 Arial;");
        enemyNameText.setLayoutX(100);
        enemyNameText.setLayoutY(220);
        Label rightLine = new Label("");
        rightLine.setStyle("-fx-font: 20 Arial;");
        rightLine.setLayoutX(100);
        rightLine.setLayoutY(425);
        Label rightScore = new Label("");
        rightScore.setStyle("-fx-font: 20 Arial;");
        rightScore.setLayoutX(100);
        rightScore.setLayoutY(525);
        middleGround.getChildren().addAll(enemyNameText, rightLine, rightScore);

        Form left = leftNextObj;
        leftGround.getChildren().addAll(left.a, left.b, left.c, left.d);
        moveOnKeyPress(left, "left");
        leftObject = left;
        leftNextObj = Controller.makeRect();

        Form right = rightNextObj;
        rightGround.getChildren().addAll(right.a, right.b, right.c, right.d);
        moveOnKeyPress(right, "right");
        rightObject = right;
        rightNextObj = Controller.makeRect();

        stage.setScene(scene2);
        stage.show();

        Timer leftFall = new Timer();
        TimerTask leftTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (leftObject.a.getY() == 0 || leftObject.b.getY() == 0 || leftObject.c.getY() == 0
                                || leftObject.d.getY() == 0)
                            leftTop++;
                        else
                            leftTop = 0;

                        if (leftTop == 2) {
                            // GAME OVER
                            Text over = new Text("GAME OVER");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arial;");
                            over.setY(250);
                            over.setX(10);
                            leftGround.getChildren().add(over);
                            leftGame = false;
                        }

                        if (leftTop == 15) {
                            // Exit
                            System.exit(0);
                        }

                        if (leftGame) {
                            MoveDown(leftObject, "left");
                            leftLine.setText(Integer.toString(leftLinesNum));
                            leftScore.setText(Integer.toString(leftScoreNum));
                        }
                    }
                });
            }
        };
        leftFall.schedule(leftTask, 0, 500);

        Timer rightFall = new Timer();
        TimerTask rightTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (rightObject.a.getY() == 0 || rightObject.b.getY() == 0 || rightObject.c.getY() == 0
                                || rightObject.d.getY() == 0)
                            rightTop++;
                        else
                            rightTop = 0;

                        if (rightTop == 2) {
                            // GAME OVER
                            Text over = new Text("GAME OVER");
                            over.setFill(Color.RED);
                            over.setStyle("-fx-font: 70 arial;");
                            over.setY(250);
                            over.setX(10);
                            rightGround.getChildren().add(over);
                            rightGame = false;
                        }
                        // Exit
                        if (rightTop == 15) {
                            System.exit(0);
                        }

                        if (rightGame) {
                            MoveDown(rightObject, "right");
                            rightLine.setText(Integer.toString(rightLinesNum));
                            rightScore.setText(Integer.toString(rightScoreNum));
                        }
                    }
                });
            }
        };
        rightFall.schedule(rightTask, 0, 500);
    }

    // need to make another method for right
    private static void moveOnKeyPress(Form form, String who) {
        scene2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                        Controller.MoveRight(form, who);
                        break;
                    case DOWN:
                        // score increase
                        MoveDown(form, who);
                        if(who.equals("left"))
                            leftScoreNum++;
                        else if(who.equals("right"))
                            rightScoreNum++;
                        break;
                    case LEFT:
                        Controller.MoveLeft(form, who);
                        break;
                    case UP:
                        MoveTurn(form, who);
                        break;
                }
            }
        });
    }

    private static void MoveTurn(Form form, String who) {
        int f = form.form;
        Rectangle a = form.a;
        Rectangle b = form.b;
        Rectangle c = form.c;
        Rectangle d = form.d;
        switch (form.getName()) {
            case "j":
                if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "l":
                if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveRight(form.b);
                    MoveRight(form.b);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    MoveDown(form.b);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveLeft(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.b);
                    MoveUp(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case "o":
                break;
            case "s":
                if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveUp(form.d);
                    MoveUp(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveDown(form.d);
                    MoveDown(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "t":
                if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
                    MoveRight(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.d);
                    MoveUp(form.d);
                    MoveUp(form.c);
                    MoveRight(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
                    MoveLeft(form.a);
                    MoveUp(form.a);
                    MoveRight(form.d);
                    MoveDown(form.d);
                    MoveDown(form.c);
                    MoveLeft(form.c);
                    form.changeForm();
                    break;
                }
                break;
            case "z":
                if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveLeft(form.c);
                    MoveUp(form.c);
                    MoveLeft(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveRight(form.c);
                    MoveDown(form.c);
                    MoveRight(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;
            case "i":
                if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
                    MoveUp(form.a);
                    MoveUp(form.a);
                    MoveRight(form.a);
                    MoveRight(form.a);
                    MoveUp(form.b);
                    MoveRight(form.b);
                    MoveDown(form.d);
                    MoveLeft(form.d);
                    form.changeForm();
                    break;
                }
                if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
                    MoveDown(form.a);
                    MoveDown(form.a);
                    MoveLeft(form.a);
                    MoveLeft(form.a);
                    MoveDown(form.b);
                    MoveLeft(form.b);
                    MoveUp(form.d);
                    MoveRight(form.d);
                    form.changeForm();
                    break;
                }
                break;
        }
    }

    private static void RemoveRows(Pane pane, String who) {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
        ArrayList<Node> newrects = new ArrayList<Node>();
        int full = 0;
        int[][] temp = {};
        if (who.equals("left"))
            temp = leftArray;
        else if (who.equals("right"))
            temp = rightArray;

        for (int i = 0; i < temp[0].length; i++) {
            for (int j = 0; j < temp.length; j++) {
                if (temp[j][i] == 1)
                    full++;
            }
            if (full == temp.length)
                lines.add(i);
            full = 0;
        }

        if (lines.size() > 0)
            do {
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rects.add(node);
                }

                if (who.equals("left")) {
                    leftScoreNum += 50;
                    leftLinesNum++;
                } else if (who.equals("right")) {
                    rightScoreNum += 50;
                    rightLinesNum++;
                }

                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() == lines.get(0) * SIZE) {
                        temp[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        pane.getChildren().remove(node);
                    } else
                        newrects.add(node);
                }

                for (Node node : newrects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() < lines.get(0) * SIZE) {
                        temp[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 0;
                        a.setY(a.getY() + SIZE);
                    }
                }
                lines.remove(0);
                rects.clear();
                newrects.clear();
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle)
                        rects.add(node);
                }
                for (Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    try {
                        temp[(int) a.getX() / SIZE][(int) a.getY() / SIZE] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                    }
                }
                rects.clear();
            } while (lines.size() > 0);
    }

    private static void MoveDown(Rectangle rect) {
        if (rect.getY() + MOVE < YMAX)
            rect.setY(rect.getY() + MOVE);

    }

    private static void MoveRight(Rectangle rect) {
        if (rect.getX() + MOVE <= XMAX - SIZE)
            rect.setX(rect.getX() + MOVE);
    }

    private static void MoveLeft(Rectangle rect) {
        if (rect.getX() - MOVE >= 0)
            rect.setX(rect.getX() - MOVE);
    }

    private static void MoveUp(Rectangle rect) {
        if (rect.getY() - MOVE > 0)
            rect.setY(rect.getY() - MOVE);
    }

    private static void MoveDown(Form form, String who) {
        int[][] temp = {};

        if (who.equals("left"))
            temp = leftArray;
        else if (who.equals("right"))
            temp = rightArray;

        if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
                || form.d.getY() == YMAX - SIZE || moveA(form, who) || moveB(form, who) || moveC(form, who) || moveD(form, who)) {
            temp[(int) form.a.getX() / SIZE][(int) form.a.getY() / SIZE] = 1;
            temp[(int) form.b.getX() / SIZE][(int) form.b.getY() / SIZE] = 1;
            temp[(int) form.c.getX() / SIZE][(int) form.c.getY() / SIZE] = 1;
            temp[(int) form.d.getX() / SIZE][(int) form.d.getY() / SIZE] = 1;

            if (who.equals("left")) {
                RemoveRows(leftGround, who);
                Form left = leftNextObj;
                leftNextObj = Controller.makeRect();
                leftObject = left;
                leftGround.getChildren().addAll(left.a, left.b, left.c, left.d);
                moveOnKeyPress(left, "left");
            } else if (who.equals("right")) {
                RemoveRows(rightGround, who);
                Form right = rightNextObj;
                rightNextObj = Controller.makeRect();
                rightObject = right;
                rightGround.getChildren().addAll(right.a, right.b, right.c, right.d);
                moveOnKeyPress(right, "right");
            }
        }

        if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX
                && form.d.getY() + MOVE < YMAX) {
            int movea = temp[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1];
            int moveb = temp[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1];
            int movec = temp[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1];
            int moved = temp[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                form.a.setY(form.a.getY() + MOVE);
                form.b.setY(form.b.getY() + MOVE);
                form.c.setY(form.c.getY() + MOVE);
                form.d.setY(form.d.getY() + MOVE);
            }
        }
    }

    private static boolean moveA(Form form, String who) {
        if(who.equals("left"))
            return (leftArray[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1);
        else
            return (rightArray[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1);
    }

    private static boolean moveB(Form form, String who) {
        if(who.equals("left"))
            return (leftArray[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1);
        else
            return (rightArray[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1);
    }

    private static boolean moveC(Form form, String who) {
        if(who.equals("left"))
            return (leftArray[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1);
        else
            return (rightArray[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1);
    }

    private static boolean moveD(Form form, String who) {
        if(who.equals("left"))
            return (leftArray[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1);
        else
            return (rightArray[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1);
    }

    private static boolean cB(Rectangle rect, int x, int y) {
        boolean xb = false;
        boolean yb = false;
        if (x >= 0)
            xb = rect.getX() + x * MOVE <= XMAX - SIZE;
        if (x < 0)
            xb = rect.getX() + x * MOVE >= 0;
        if (y >= 0)
            yb = rect.getY() - y * MOVE > 0;
        if (y < 0)
            yb = rect.getY() + y * MOVE < YMAX;
        return xb && yb && leftArray[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;
    }
}