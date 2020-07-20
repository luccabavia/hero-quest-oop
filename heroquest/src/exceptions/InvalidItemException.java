package exceptions;

public class InvalidItemException extends Exception {

    public InvalidItemException() {
        super();
    }

    public InvalidItemException(String message) {
        super(message);
    }

    public InvalidItemException(Throwable cause) {
        super(cause);
    }

    public InvalidItemException(String message, Throwable cause) {
        super(message, cause);
    }

}
