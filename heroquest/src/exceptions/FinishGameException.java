package exceptions;

public class FinishGameException extends Exception {

    public FinishGameException() {
        super();
    }

    public FinishGameException(String message) {
        super(message);
    }

    public FinishGameException(Throwable cause) {
        super(cause);
    }

    public FinishGameException(String message, Throwable cause) {
        super(message, cause);
    }

}

