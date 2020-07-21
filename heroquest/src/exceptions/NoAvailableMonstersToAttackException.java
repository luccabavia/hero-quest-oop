package exceptions;

public class NoAvailableMonstersToAttackException extends Exception {

    public NoAvailableMonstersToAttackException() {
        super();
    }

    public NoAvailableMonstersToAttackException(String message) {
        super(message);
    }

    public NoAvailableMonstersToAttackException(Throwable cause) {
        super(cause);
    }

    public NoAvailableMonstersToAttackException(String message,
                                                Throwable cause) {
        super(message, cause);
    }

}
