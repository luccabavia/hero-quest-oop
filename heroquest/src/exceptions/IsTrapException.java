package exceptions;

public class IsTrapException extends Exception {

    public IsTrapException() {
        super();
    }

    public IsTrapException(String message) {
        super(message);
    }

    public IsTrapException(Throwable cause) {
        super(cause);
    }

    public IsTrapException(String message, Throwable cause) {
        super(message, cause);
    }

}
