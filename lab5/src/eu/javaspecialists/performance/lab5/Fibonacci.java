package eu.javaspecialists.performance.lab5;

import java.math.*;

public class Fibonacci {
    public BigInteger f(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n == 1) return BigInteger.ONE;
        return f(n - 1).add(f(n - 2));
    }
}
