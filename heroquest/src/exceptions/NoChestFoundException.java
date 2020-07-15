package exceptions;

public class NoChestFoundException extends Exception {

    public NoChestFoundException() {
        super();
    }

    public NoChestFoundException(String message) {
        super(message);
    }

    public NoChestFoundException(Throwable cause) {
        super(cause);
    }

    public NoChestFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}