package exceptions;

public class NoSpellLeftException extends Exception {

    public NoSpellLeftException() {
        super();
    }

    public NoSpellLeftException(String message) {
        super(message);
    }

    public NoSpellLeftException(Throwable cause) {
        super(cause);
    }

    public NoSpellLeftException(String message, Throwable cause) {
        super(message, cause);
    }

}
