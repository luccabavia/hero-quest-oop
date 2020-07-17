package exceptions;

public class MonstersDiedException extends Exception {

    public MonstersDiedException() {
        super();
    }

    public MonstersDiedException(String message) {
        super(message);
    }

    public MonstersDiedException(Throwable cause) {
        super(cause);
    }

    public MonstersDiedException(String message, Throwable cause) {
        super(message, cause);
    }

}
