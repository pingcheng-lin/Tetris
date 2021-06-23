import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;

public class Pattern {
    // Every pattern has 4 element
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    private String name;
    public int form = 1;

    public Pattern() {
    }

    public Pattern(Rectangle a, Rectangle b, Rectangle c, Rectangle d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Pattern(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        // give special color
        Stop[] stops = new Stop[] { new Stop(0, Color.GRAY), new Stop(1, Color.BLUE) };
        LinearGradient lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        // fill whole rectangle
        this.a.setFill(lg);
        this.b.setFill(lg);
        this.c.setFill(lg);
        this.d.setFill(lg);
    }

    public void changeForm() {
        if (form != 4)
            form++;
        else
            form = 1;
    }

    public String getName() {
        return name;
    }
}