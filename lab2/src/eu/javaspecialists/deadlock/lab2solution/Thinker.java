package eu.javaspecialists.deadlock.lab2solution;

import eu.javaspecialists.deadlock.lab2.*;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * Our philosopher always first locks left, then right.  If all of the thinkers
 * sit in a circle and their threads call "drink()" at the same time, then they
 * will end up with a deadlock.
 * <p>
 * In our solution we did not add a random sleep, but you might need to do this
 * if you have a lot of conflicts.
 * <p>
 * A fun thing to try is to count how many times you had to back off when you
 * could not acquire a lock.
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
        while (true) {
            left.lock();
            try {
                if (right.tryLock()) {
                    try {
                        drinking();
                        return; // remember to return after a good drink
                    } finally {
                        right.unlock();
                    }
                }
            } finally {
                left.unlock();
            }
            // Possibly add a short random sleep to avoid a livelock, but only
            // do this after you have unlocked both locks.
            LockSupport.parkNanos(System.nanoTime() & 0xffff);
        }
    }

    private void drinking() {
        if (!left.isHeldByCurrentThread() || !right.isHeldByCurrentThread()) {
            throw new IllegalMonitorStateException("Not holding both locks");
        }
        System.out.printf("(%d) Drinking%n", id);
        drinks++;
    }

    public void think() {
        System.out.printf("(%d) Thinking%n", id);
    }
}
