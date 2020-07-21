package exceptions;

public class HeroDiedException extends Exception {

    public HeroDiedException() {
        super();
    }

    public HeroDiedException(String message) {
        super(message);
    }

    public HeroDiedException(Throwable cause) {
        super(cause);
    }

    public HeroDiedException(String message, Throwable cause) {
        super(message, cause);
    }

}
