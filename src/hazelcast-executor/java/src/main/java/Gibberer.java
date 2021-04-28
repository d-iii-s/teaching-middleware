import java.io.Serializable;
import java.lang.Runnable;

public class Gibberer implements Serializable, Runnable {

    private final String text;

    public Gibberer (String text) {
        this.text = text;
    }

    public void run () {
        System.out.println (text);
    }
}
