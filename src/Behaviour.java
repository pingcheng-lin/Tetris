import javafx.scene.shape.Rectangle;

public class Behaviour {
    // Getting the numbers and the leftArray from Tetris
    public static final int MOVE = GroundController.MOVE;
    public static final int SIZE = GroundController.SIZE;
    public static int XMAX = GroundController.XMAX;
    public static int YMAX = GroundController.YMAX;
    public static int[][] leftArray = GroundController.leftArray;
    public static int[][] rightArray = GroundController.rightArray;

    // provide for right keyboard input
    public static void MoveRight(Pattern form, String who) {
        if (form.a.getX() + MOVE <= XMAX - SIZE && form.b.getX() + MOVE <= XMAX - SIZE
                && form.c.getX() + MOVE <= XMAX - SIZE && form.d.getX() + MOVE <= XMAX - SIZE) {
            int[][] temp = {};
            if (who.equals("left"))
                temp = leftArray;
            else if (who.equals("right"))
                temp = rightArray;
            // if there is no rect when a move to that place
            int offseta = temp[((int) form.a.getX() / SIZE) + 1][((int) form.a.getY() / SIZE)];
            int offsetb = temp[((int) form.b.getX() / SIZE) + 1][((int) form.b.getY() / SIZE)];
            int offsetc = temp[((int) form.c.getX() / SIZE) + 1][((int) form.c.getY() / SIZE)];
            int offsetd = temp[((int) form.d.getX() / SIZE) + 1][((int) form.d.getY() / SIZE)];
            if (offseta == 0 && offseta == offsetb && offsetb == offsetc && offsetc == offsetd) {
                form.a.setX(form.a.getX() + MOVE);
                form.b.setX(form.b.getX() + MOVE);
                form.c.setX(form.c.getX() + MOVE);
                form.d.setX(form.d.getX() + MOVE);
            }
        }
    }

    // provide for left keyboard input
    public static void MoveLeft(Pattern form, String who) {
        if (form.a.getX() - MOVE >= 0 && form.b.getX() - MOVE >= 0 && form.c.getX() - MOVE >= 0
                && form.d.getX() - MOVE >= 0) {
            int[][] temp = {};
            if (who.equals("left"))
                temp = leftArray;
            else if (who.equals("right"))
                temp = rightArray;
            // if there is no rect when a move to that place
            int offseta = temp[((int) form.a.getX() / SIZE) - 1][((int) form.a.getY() / SIZE)];
            int offsetb = temp[((int) form.b.getX() / SIZE) - 1][((int) form.b.getY() / SIZE)];
            int offsetc = temp[((int) form.c.getX() / SIZE) - 1][((int) form.c.getY() / SIZE)];
            int offsetd = temp[((int) form.d.getX() / SIZE) - 1][((int) form.d.getY() / SIZE)];
            if (offseta == 0 && offseta == offsetb && offsetb == offsetc && offsetc == offsetd) {
                form.a.setX(form.a.getX() - MOVE);
                form.b.setX(form.b.getX() - MOVE);
                form.c.setX(form.c.getX() - MOVE);
                form.d.setX(form.d.getX() - MOVE);
            }
        }
    }

    public static Pattern makeRect(int seed) {
        // 7 kinds of pattern of rectangle
        // -1 is to give the boundary between two rectangle
        Rectangle a = new Rectangle(SIZE - 1, SIZE - 1), b = new Rectangle(SIZE - 1, SIZE - 1),
                c = new Rectangle(SIZE - 1, SIZE - 1), d = new Rectangle(SIZE - 1, SIZE - 1);
        String name;
        if (seed == 1) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            name = "j";
        } else if (seed == 2) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2 - SIZE);
            b.setY(SIZE);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            d.setY(SIZE);
            name = "l";
        } else if (seed == 3) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 - SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2);
            d.setY(SIZE);
            name = "o";
        } else if (seed == 4) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 - SIZE);
            d.setY(SIZE);
            name = "s";
        } else if (seed == 5) {
            a.setX(XMAX / 2 - SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE);
            name = "t";
        } else if (seed == 6) {
            a.setX(XMAX / 2 + SIZE);
            b.setX(XMAX / 2);
            c.setX(XMAX / 2 + SIZE);
            c.setY(SIZE);
            d.setX(XMAX / 2 + SIZE + SIZE);
            d.setY(SIZE);
            name = "z";
        } else {
            a.setX(XMAX / 2 - SIZE - SIZE);
            b.setX(XMAX / 2 - SIZE);
            c.setX(XMAX / 2);
            d.setX(XMAX / 2 + SIZE);
            name = "i";
        }
        return new Pattern(a, b, c, d, name);
    }
}