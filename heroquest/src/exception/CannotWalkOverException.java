package exception;

public class CannotWalkOverException extends Exception {

    public CannotWalkOverException() {
        super();
    }

    public CannotWalkOverException(String message) {
        super(message);
    }

    public CannotWalkOverException(Throwable cause) {
        super(cause);
    }

    public CannotWalkOverException(String message, Throwable cause) {
        super(message, cause);
    }

}
