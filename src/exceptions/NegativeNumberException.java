package exceptions;

public class NegativeNumberException extends Exception {
    public NegativeNumberException() {
        System.out.println("Negative numbers exception thrown!");
    }

    public NegativeNumberException(String message) {
        System.out.println(message);
    }
}
