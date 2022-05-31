package exceptions;

public class InvalidNumberException extends Exception {
    public InvalidNumberException() {
        System.out.println("Negative numbers exception thrown!");
    }

    public InvalidNumberException(String message) {
        System.out.println(message);
    }
}
