package exceptions;

public class UnknownItemException extends Throwable {

    public UnknownItemException() {
        super();
    }

    public UnknownItemException(String message) {
        super(message);
    }

    public UnknownItemException(Throwable cause) {
        super(cause);
    }

    public UnknownItemException(String message, Throwable cause) {
        super(message, cause);
    }

}
