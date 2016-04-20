package eu.javaspecialists.deadlock.lab1solution;

import eu.javaspecialists.deadlock.lab1.*;

import java.util.concurrent.*;

/**
 * Our philosopher always first locks left, then right.  If all of the thinkers
 * sit in a circle and their threads call "drink()" at the same time, then they
 * will end up with a deadlock.
 * <p>
 * In our solution, we decide in the constructor which the larger and smaller
 * locks are, rather than doing this every time the drink() method is called.
 *
 * @author Heinz Kabutz
 */
public class Thinker implements Callable<ThinkerStatus> {
    private final int id;
    private final Krasi bigger, smaller;
    private int drinks = 0;

    public Thinker(int id, Krasi left, Krasi right) {
        this.id = id;
        this.bigger = left.compareTo(right) > 0 ? left : right;
        this.smaller = bigger == left ? right : left;
    }

    public ThinkerStatus call() throws Exception {
        for (int i = 0; i < 1000; i++) {
            drink();
            think();
        }
        return drinks == 1000 ? ThinkerStatus.HAPPY_THINKER :
            ThinkerStatus.UNHAPPY_THINKER;
    }

    public void drink() {
        synchronized (bigger) {
            synchronized (smaller) {
                drinking();
            }
        }
    }

    private void drinking() {
        if (!Thread.holdsLock(bigger) || !Thread.holdsLock(smaller)) {
            throw new IllegalMonitorStateException("Not holding both locks");
        }
        System.out.printf("(%d) Drinking%n", id);
        drinks++;
    }

    public void think() {
        System.out.printf("(%d) Thinking%n", id);
    }
}
