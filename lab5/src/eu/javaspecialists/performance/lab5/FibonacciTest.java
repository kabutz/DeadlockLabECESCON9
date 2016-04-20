package eu.javaspecialists.performance.lab5;

import org.junit.*;

import static org.junit.Assert.*;

public class FibonacciTest {
    @Test
    public void testSimpleValues() {
        Fibonacci fib = new Fibonacci();
        assertEquals("0", fib.f(0).toString());
        assertEquals("1", fib.f(1).toString());
        assertEquals("1", fib.f(2).toString());
        assertEquals("2", fib.f(3).toString());
        assertEquals("3", fib.f(4).toString());
        assertEquals("5", fib.f(5).toString());
        assertEquals("8", fib.f(6).toString());
        assertEquals("13", fib.f(7).toString());
        assertEquals("21", fib.f(8).toString());
        assertEquals("34", fib.f(9).toString());
        assertEquals("55", fib.f(10).toString());
    }

    @Test
    public void test1_000() {
        long time = System.currentTimeMillis();
        Fibonacci fib = new Fibonacci();
        assertEquals(324, fib.f(1_000).bitCount());
        time = System.currentTimeMillis() - time;
        System.out.println("test1_000() time = " + time);
    }

    @Test
    public void test1_000_000() {
        long time = System.currentTimeMillis();
        Fibonacci fib = new Fibonacci();
        assertEquals(347084, fib.f(1_000_000).bitCount());
        time = System.currentTimeMillis() - time;
        System.out.println("test1_000_000() time = " + time);
    }

    @Test
    public void test10_000_000() {
        long time = System.currentTimeMillis();
        Fibonacci fib = new Fibonacci();
        assertEquals(3471105, fib.f(10_000_000).bitCount());
        time = System.currentTimeMillis() - time;
        System.out.println("test10_000_000() time = " + time);
    }

    @Test
    public void test100_000_000() {
        long time = System.currentTimeMillis();
        Fibonacci fib = new Fibonacci();
        assertEquals(34712631, fib.f(100_000_000).bitCount());
        time = System.currentTimeMillis() - time;
        System.out.println("test100_000_000() time = " + time);
    }
}
