package eu.javaspecialists.deadlock.lab2solution;

import eu.javaspecialists.deadlock.lab2.*;
import eu.javaspecialists.deadlock.util.*;

/**
 * Launcher to test whether the symposium ends in a deadlock.  Hopefully the
 * deadlock has now disappeared.
 * <p>
 * DO NOT CHANGE THIS CODE!
 *
 * @author Henri Tremblay, Heinz Kabutz
 */
public class Main {
    public static void main(String... args) throws InterruptedException {
        Symposium symposium = new Symposium(5);
        ThinkerStatus status = symposium.run();
        if (status == ThinkerStatus.UNHAPPY_THINKER) {
            System.err.println("Probably a deadlock (or incorrect code)");
            return;
        }

        DeadlockTester tester = new DeadlockTester();
        tester.checkThatCodeDoesNotDeadlock(new Runnable() {
            public void run() {
                try {
                    new Symposium(5).run();
                } catch (InterruptedException e) {
                    return;
                }
            }
        });

        System.out.println("No deadlock detected");
    }
}
