package exception;

public class PositionDoesNotExistException extends Exception {

    public PositionDoesNotExistException() {
        super();
    }

    public PositionDoesNotExistException(String message) {
        super(message);
    }

    public PositionDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public PositionDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

}
