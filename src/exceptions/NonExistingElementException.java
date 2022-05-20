package exceptions;

public class NonExistingElementException extends  Exception {
    public NonExistingElementException() {
        System.out.println("Non Existing Element Exception Thrown!");
    }

    public NonExistingElementException(String message) {
        System.out.println(message);
    }
}
