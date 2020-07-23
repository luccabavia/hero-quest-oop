package exceptions;

public class InvalidGeneratorSeedException extends Exception {

    public InvalidGeneratorSeedException() {
        super();
    }

    public InvalidGeneratorSeedException(String message) {
        super(message);
    }

    public InvalidGeneratorSeedException(Throwable cause) {
        super(cause);
    }

    public InvalidGeneratorSeedException(String message, Throwable cause) {
        super(message, cause);
    }

}
