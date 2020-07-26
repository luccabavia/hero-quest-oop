package exceptions;

public class IsTrapException extends Exception {

    private int[] trapPosition;
    private int damage;

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

    public IsTrapException(String message, int x, int y, int damage) {
        super(message);
        this.trapPosition = new int[] {x, y};
        this.damage = damage;
    }

    public int[] getTrapPosition() {
        return this.trapPosition;
    }

    public int getDamage() {
        return this.damage;
    }
}
