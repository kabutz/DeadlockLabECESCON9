package eu.javaspecialists.deadlock.lab1;

import java.util.concurrent.*;

/**
 * Our philosopher always first locks left, then right.  If all of the thinkers
 * sit in a circle and their threads call "drink()" at the same time, then they
 * will end up with a deadlock.
 * <p>
 * Instead of locking first on left and then on right, change the code to lock
 * on the bigger Krasi first.  This will avoid the deadlock, as we will always
 * lock in the same order.
 *
 * @author Heinz Kabutz
 */
public class Thinker implements Callable<ThinkerStatus> {
    private final int id;
    private final Krasi left, right;
    private int drinks = 0;

    public Thinker(int id, Krasi left, Krasi right) {
        this.id = id;
        this.left = left;
        this.right = right;
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
        synchronized (left) {
            synchronized (right) {
                drinking();
            }
        }
    }

    private void drinking() {
        if (!Thread.holdsLock(left) || !Thread.holdsLock(right)) {
            throw new IllegalMonitorStateException("Not holding both locks");
        }
        System.out.printf("(%d) Drinking%n", id);
        drinks++;
    }

    public void think() {
        System.out.printf("(%d) Thinking%n", id);
    }
}
